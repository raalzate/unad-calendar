package com.espaciounido.unadcalendar;



import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;
import com.espaciounido.unadcalendar.mock.MainActivityPresenterMock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by MyMac on 21/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    MainActivityPresenterMock mainActivityPresenterMock;

    @Mock
    IMainActivity mainActivity;

    @Before public void setUp(){
        mainActivityPresenterMock = new MainActivityPresenterMock(mainActivity);
    }


    @Test public void verify_completedLoad_HomeFragment(){
        SessionUserFragment fragment = mock(SessionUserFragment.class);
        mainActivityPresenterMock.onLoadFragment(fragment.getClass());
        verify(mainActivity, times(1))
                .onLoadedFragment(IMainActivity.Status.COMPLETED);
    }

    



}
