package com.espaciounido.unadcalendar.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.espaciounido.unadcalendar.model.Diarys;
import com.espaciounido.unadcalendar.model.Event;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc

/**
 * Class Utils.
 *
 * @author Raul .A Alzate <raalzate@everis.com>
 * @since 13/11/2014
 */
public class Utils {

    /**
     * Variable token.
     */
    private static String token = "";
    private static TinyDB mTinyDB;

    //secret construct
    private Utils() {}

    /**
     * Funcion que valida la fecha final.
     *
     * @param finishDate para finish date
     * @return true, si todo esta correcto
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since 13/11/2014
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean hasDateValid(String finishDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strDate;
        try {
            strDate = sf.parse(finishDate);
            if (new Date().after(strDate)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public static  Calendar strToCalendar(String input) {
        Calendar d = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            d.setTime(sdf.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static Calendar strToCalendar(String input, String formate) {
        Calendar d = Calendar.getInstance();
        int yearNow = d.get(Calendar.YEAR);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formate, new Locale("es_ES"));
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            d.setTime(sdf.parse(input));
            d.set(Calendar.YEAR, yearNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static long strToMillisec(String input) {
        Calendar d = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            d.setTime(sdf.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d.getTimeInMillis();
    }

    /**
     * Pregunta si tiene internet.
     *
     * @param context para context
     * @return boolean
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since 27/10/2014
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo Get para data now.
     *
     * @return String
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since 13/11/2014
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDataNow() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataByFormat(Calendar date, String formater) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(formater);
        Date now = date.getTime();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    /**
     * Metodo Get para time now.
     *
     * @return String
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since 27/11/2014
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeNow() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }


    /**
     * Clear data.
     *
     * @param context para context
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since Dec 23, 2014
     */
    public static void clearData(Context context) {
        SharedPreferences settings = context.
                getSharedPreferences("preferencesUtils", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    /**
     * Metodo Get para mac address.
     *
     * @param context para context
     * @return String
     * @author Raul .A Alzate <raalzate@everis.com>
     * @since 13/11/2014
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }


    public static String dateFormatBasic(String fecha) {

        long dStart = Utils.strToMillisec(fecha);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dStart);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd hh:mm a", new Locale("es_ES"));

        return sdf.format(c.getTime());
    }

    public static String dateFormatBasic(String fecha, String fomater) {

        long dStart = Utils.strToMillisec(fecha);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dStart);
        SimpleDateFormat sdf = new SimpleDateFormat(fomater, new Locale("es_ES"));

        return sdf.format(c.getTime());
    }

    public static void startNotification(Context context, Class gotoClass) {
        TinyDB tinyDB = getTinyDB(context);
        try {
            int hour = Integer.parseInt(tinyDB.getString("active_frequency"));
            new AlarmService(context).startAlarm(hour, 0);
        } catch (Exception e) {
            context.startActivity(new Intent(context, gotoClass));
            Toast.makeText(context, "Nueva configuracion",
                    Toast.LENGTH_LONG).show();
        }
    }



    public static TinyDB getTinyDB(Context context) {
        if (mTinyDB == null)
            mTinyDB = new TinyDB(context);
        return mTinyDB;
    }



    public static int getProgress(Event event) {

        int daysResta = getHoursResta(event);
        int daysTotal = getHoursTotal(event);
        int progress = ((daysTotal - daysResta) * 100) / daysTotal;

        return progress;
    }

    public static int getHoursResta(Event event) {
        Date myDateEnd = new Date();
        myDateEnd.setTime(Utils.strToMillisec(event.getEnd()));
        return Hours.hoursBetween(new DateTime(new Date()),
                new DateTime(myDateEnd)).getHours();
    }


    public static boolean validateEmail(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static int getHoursTotal(Event event) {

        Date myDateEnd = new Date();
        myDateEnd.setTime(Utils.strToMillisec(event.getEnd()));
        Date myDateStart = new Date();
        myDateStart.setTime(Utils.strToMillisec(event.getStart()));

        return Hours.hoursBetween(new DateTime(myDateStart),
                new DateTime(myDateEnd)).getHours();
    }

}
