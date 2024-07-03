package com.vishal2376.scrollblock.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vishal2376.scrollblock.domain.model.AppUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertAppUsage(appUsage: AppUsage): Long

	@Update
	suspend fun updateAppUsage(appUsage: AppUsage)

	@Query("SELECT * FROM AppUsage WHERE packageName = :packageName AND date = :date LIMIT 1")
	suspend fun getAppUsageByPackageAndDate(packageName: String, date: String): AppUsage?

	@Query("SELECT * FROM AppUsage WHERE packageName = :appPackageName")
	fun getAppUsageByPackageName(appPackageName: String): Flow<List<AppUsage>>

	@Query("SELECT * FROM AppUsage")
	fun getAllAppUsage(): Flow<List<AppUsage>>

	@Query("SELECT * FROM AppUsage WHERE date = :date")
	fun getAppUsageByDate(date: String): Flow<List<AppUsage>>

	@Query("SELECT * FROM AppUsage WHERE date BETWEEN :startDate AND :endDate")
	fun getAppUsageByDateRange(startDate: String, endDate: String): Flow<List<AppUsage>>
}
