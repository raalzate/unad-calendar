package com.espaciounido.unadcalendar.course;

import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.espaciounido.unadcalendar.MainApp;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.course.domain.model.Course;
import com.espaciounido.unadcalendar.course.domain.model.CreateCalendar;
import com.espaciounido.unadcalendar.course.domain.usecase.CreateGCCalendarUseCase;
import com.espaciounido.unadcalendar.course.domain.usecase.GetCourseUseCase;
import com.espaciounido.unadcalendar.course.domain.usecase.RegisterCourseUseCase;
import com.espaciounido.unadcalendar.data.repo.FirebaseReferences;
import com.espaciounido.unadcalendar.data.repo.course.CourseRemoteRepo;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarLocalRepo;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarRemoteRepo;
import com.espaciounido.unadcalendar.data.rest.UnadCalendarApi;
import com.espaciounido.unadcalendar.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 5/09/16.
 */
public class SearcherCoursePresenter implements SearcherCourse.Presenter {

    private static final String TAG = "SearcherCoursePresenter";

    private final RegisterCourseUseCase registerCourseUseCase;
    private final CreateGCCalendarUseCase createGCCalendarUseCase;
    private final GetCourseUseCase getCourseUseCase;

    private SearcherCourse.View view;
    private CourseAdapter adapter;
    private List<Course> courses;
    private Snackbar snarkbar;
    private boolean isOnStop = false;
    private UseCaseHandler useCaseHandler;

    public SearcherCoursePresenter(SearcherCourse.View view, UseCaseHandler useCaseHandler) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;

        UnadCalendarApi api = MainApp.getRestClient().getUnadCalendarApi();
        DatabaseReference ref = FirebaseReferences.getGCCalendar();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getCourseUseCase = new GetCourseUseCase(new CourseRemoteRepo(api));
        registerCourseUseCase = new RegisterCourseUseCase(new CourseRemoteRepo(api),
                new GCCalendarRemoteRepo(ref, uid));
        createGCCalendarUseCase = new CreateGCCalendarUseCase(new GCCalendarLocalRepo());

    }

    @Override
    public void loadCourses() {

        if (Utils.isOnline(view.getBaseContext())) {
            loadDataForFilter();
        } else {
            snarkbar = view.createSnackbar("No tiene internet.");
            snarkbar.show();
        }
    }

    private void loadDataForFilter() {
        view.showProgress();
        useCaseHandler.execute(getCourseUseCase, new GetCourseUseCase.Request(),
                new UseCase.UseCaseCallback<GetCourseUseCase.Response>() {
                    @Override
                    public void onSuccess(GetCourseUseCase.Response response) {
                        if (!isOnStop) {
                            SearcherCoursePresenter.this.courses = response.getCourses();
                            adapter = new CourseAdapter(SearcherCoursePresenter.this.courses,
                                    SearcherCoursePresenter.this);
                            view.setAdapter(adapter);
                            view.hideProgress();
                        }
                    }

                    @Override
                    public void onError() {
                        if (!isOnStop) {
                            if (snarkbar != null) {
                                snarkbar.dismiss();
                            }
                            view.showDialogAlert("Error", "No es posible encontrar el curso, por favor intente mas tarde.");
                            view.hideProgress();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        isOnStop = true;
        getCourseUseCase.cancelUseCase();
        registerCourseUseCase.cancelUseCase();
        registerCourseUseCase.cancelUseCase();
        if (snarkbar != null) snarkbar.dismiss();
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        adapter.setFilter(courses);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (courses != null) {
            final List<Course> filteredModelList = filter(courses, newText);
            adapter.setFilter(filteredModelList);
        }
        return true;
    }

    @Override
    public List<Course> filter(List<Course> models, String query) {
        query = query.toLowerCase();
        final List<Course> filteredModelList = new ArrayList<>();
        for (Course model : models) {
            final String text = model.name.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onItemClick(final int position) {
        snarkbar = view.createSnackbar("Buscando calendarios...");
        snarkbar.show();

        String email = MainApp.getPreferenceModel().getEmail();
        String period = MainApp.getPreferenceModel().getPeriod();
        String code = adapter.getItem(position).code;
        CreateCalendar createCalendar = new CreateCalendar(code, email, period);

        useCaseHandler.execute(registerCourseUseCase,
                new RegisterCourseUseCase.Request(createCalendar),
                new UseCase.UseCaseCallback<RegisterCourseUseCase.Response>() {
                    @Override
                    public void onSuccess(RegisterCourseUseCase.Response response) {
                        saveCalendarAndPubNew(response.getData(), adapter.getItem(position));
                    }

                    @Override
                    public void onError() {
                        if (!isOnStop) {
                            if (snarkbar != null) {
                                snarkbar.dismiss();
                            }
                            view.showDialogAlert("Error", "No es posible guardar el curso, por favor intente mas tarde.");
                            view.hideProgress();
                        }
                    }
                });

    }

    private void saveCalendarAndPubNew(GCCalendar calendar, final Course course) {
        snarkbar.setText("Guardando...");
        useCaseHandler.execute(createGCCalendarUseCase,
                new CreateGCCalendarUseCase.Request(calendar, course),
                new UseCase.UseCaseCallback<CreateGCCalendarUseCase.Response>() {
                    @Override
                    public void onSuccess(CreateGCCalendarUseCase.Response response) {
                        snarkbar.dismiss();
                        view.resultCourse(course);
                    }

                    @Override
                    public void onError() {
                        if (!isOnStop) {
                            if (snarkbar != null) {
                                snarkbar.dismiss();
                            }
                            view.showDialogAlert("Error", "No es crear el curso, por favor intente mas tarde.");
                            view.hideProgress();
                        }
                    }
                });
    }


}
