package data.multiaccounts.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.multiaccounts.converters.DateConverter
import data.multiaccounts.models.AccountResponse
import data.multiaccounts.sources.AccountInfoSource

@Database(entities = [AccountResponse::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountInfoSource(): AccountInfoSource
}
