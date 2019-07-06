package com.example.bracketsol.sparrow;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Interface.ItemDAO;
import com.example.bracketsol.sparrow.Model.RoomItem;
import com.example.bracketsol.sparrow.Utils.Prefs;

import java.util.List;


@Database(entities = {RoomItem.class}, exportSchema = false, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_Name = "mydb";
    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_Name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }

    public abstract ItemDAO getItemDAO();

    public void InsertDataToInternalDatabase(int internalId,
                                              int userid,
                                              String getusername,
                                              String getfullname,
                                              String email,
                                              String phone,
                                              String getprofession) {
        RoomItem roomItem = new RoomItem(internalId,userid,getusername, getfullname,email, phone,getprofession);
        Log.i("db", "db Data" +roomItem.getId()+roomItem.getUId() + roomItem.getUsername() + roomItem.getEmail() + roomItem.getPhone() );
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(MyApp.getContext()).getItemDAO().insert(roomItem);
                Log.i("db", "db Data" +roomItem.getId()+roomItem.getUId() + roomItem.getUsername() + roomItem.getEmail() + roomItem.getPhone() );
            }
        });

    }


    public void getUsersData(Context context){

        final List<RoomItem> persons = getItemDAO().getItems();
        //Log.i("size" ,"size "+ persons.size()+"id"+Prefs.getUserIDFromPref(MyApp.getContext()));

        for (int a = 0; a < persons.size(); a++) {
            int InternalId = persons.get(a).getId();
            int userId = persons.get(a).getUId();
            String fullname = persons.get(a).getUsername();
            String email= persons.get(a).getEmail();
            String phone= persons.get(a).getPhone();
            Log.i("out", "" + InternalId);
            Log.i("out", "" + userId);
            Log.i("out", "" + fullname);
            Log.i("out", "" + email);
            Log.i("out", "" + phone);
        }
    }

    public int gettUserIDFromDB(Context context) {

        int internalid = 0;
        final List<RoomItem> persons = getItemDAO().getItemByID(Prefs.getUserIDFromPref(MyApp.getContext()));
        for (int a = 0; a < persons.size(); a++) {
            int InternalId = persons.get(a).getId();
            Log.i("out", "" + InternalId);
            }
        return internalid;
    }
}


