package com.espaciounido.unadcalendar.model;

import android.content.Context;
import android.util.Log;

import com.espaciounido.unadcalendar.db.C2DBOpenHelper;
import com.espaciounido.unadcalendar.db.CalendarUNADDB;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * Class ModelSerializableStore.
 *
 * @author Raul .A Alzate <raalzate@everis.com>
 * @since 26/11/2014
 */
public class ModelSerializableStore {

	/** Variable m context. */
	private Context mContext;
	
	/** Variable m mapper. */
	private ObjectMapper mMapper;
	
	/** Variable m c2 db open helper. */
	private C2DBOpenHelper mC2DBOpenHelper;
;


	/**
	 * Instancia de clase ModelSerializableStore.
	 * Metodo constructor.
	 *
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param context para context
	 * @since 26/11/2014
	 */
	public ModelSerializableStore(Context context) {
		mContext = context;
		mMapper = new ObjectMapper();
		mC2DBOpenHelper =  new C2DBOpenHelper(context);
	}

	/**
	 * Save content node.
	 *
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param contentNode para content node
	 * @param tmp para tmp
	 * @since 26/11/2014
	 */
	public void saveEvent(Diarys diary, String id) {
		try {
			mC2DBOpenHelper.open()
			  .insertModelSerializable(id, mMapper.writeValueAsString(diary));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mC2DBOpenHelper.close();
	}

	/**
	 * Esta funcion se encarga de actualizar el evento
	 * @param diary
	 * @param id
	 */
	public void updateEvent(Diarys diary, String id){
		if(deleteDiary(id))
			saveEvent(diary,id);
	}


    public  ArrayList<Diarys> getDiarys(){
        List<HashMap<String, String>> list = mC2DBOpenHelper.open().getAllModelSerializable();
        Iterator it = list.iterator();
        ArrayList<Diarys> diarys = new ArrayList<Diarys>();
        try {
            while (it.hasNext()){
                HashMap<String, String> element = (HashMap<String, String>)it.next();
                String data = element.get(CalendarUNADDB.MODELSERIALIZABLE.C2_MODEL);
                diarys.add(mMapper.readValue(data, Diarys.class));
            }
        } catch (Exception e) {
            Log.e("ModelSerializableStore", "ModelSerializableStore: "+e.getMessage());
        }
        return diarys;
    }

    public boolean deleteDiary(String id){
       return mC2DBOpenHelper.open().deleteModelByKey(id);
    }

	public Diarys getDiary(String id) {
		try {
			String data = mC2DBOpenHelper.open().getModelByKey(id);
			mC2DBOpenHelper.close();
			return mMapper.readValue(data,Diarys.class);
		} catch (Exception e) {
			Log.e("CARTELERAS", "ModelSerializableStore: "+e.getMessage());
		}

		return null;
	}
	

	public static void clearModelSerializableStore(Context context) {
		 new C2DBOpenHelper(context)
		 	.open().clearData(CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE).close();
	}
}
