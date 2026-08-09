package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DesignItemDao {
    @Query("SELECT * FROM design_items")
    fun getAllItems(): Flow<List<DesignItemEntity>>

    @Query("SELECT * FROM design_items WHERE type = :type")
    fun getItemsByType(type: String): Flow<List<DesignItemEntity>>

    @Query("SELECT * FROM design_items WHERE id = :id")
    fun getItemById(id: String): Flow<DesignItemEntity?>

    @Query("SELECT * FROM design_items WHERE id = :id")
    suspend fun getItemByIdSync(id: String): DesignItemEntity?

    @Query("SELECT COUNT(*) FROM design_items")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DesignItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: DesignItemEntity)

    @Query("SELECT * FROM design_items WHERE nameFa LIKE '%' || :query || '%' OR nameEn LIKE '%' || :query || '%' OR searchKeywords LIKE '%' || :query || '%' OR taglineFa LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<DesignItemEntity>>
}
