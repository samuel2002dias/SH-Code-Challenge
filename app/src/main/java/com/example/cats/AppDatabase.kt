package com.example.cats

import android.content.Context
import androidx.room.*

/**
 * Room database for the app, holding breed and favorite entities.
 */
@Database(entities = [BreedEntity::class, FavoriteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for breed operations.
     */
    abstract fun breedDao(): BreedDao

    /**
     * Returns the DAO for favorite operations.
     */
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of the database.
         *
         * @param context Application context.
         * @return The database instance.
         */
        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cats_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}