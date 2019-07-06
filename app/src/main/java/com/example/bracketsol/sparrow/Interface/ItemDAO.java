package com.example.bracketsol.sparrow.Interface;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.ClipData;

import com.example.bracketsol.sparrow.Model.RoomItem;

import java.util.List;

@Dao
public interface ItemDAO {


    @Query("SELECT * FROM items")
    public List<RoomItem> getItems();

    @Query("SELECT * FROM items WHERE id = :id")
    public List<RoomItem> getItemByID(int id);

    @Insert
    public void insert(RoomItem items);
    @Update
    public void update(RoomItem items);
    @Delete
    public void delete(RoomItem item);

}
