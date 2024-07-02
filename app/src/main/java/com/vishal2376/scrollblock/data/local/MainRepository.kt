package com.vishal2376.scrollblock.data.local

import com.vishal2376.scrollblock.domain.model.AppUsage
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class MainRepository @Inject constructor(
	private val appUsageDao: AppUsageDao
) {

	fun getAllAppUsage(): Flow<List<AppUsage>> {
		return appUsageDao.getAllAppUsage()
	}

	fun getAppUsageByDate(date: String): Flow<List<AppUsage>> {
		return appUsageDao.getAppUsageByDate(date)
	}

	fun getAppUsageByPackageName(packageName: String): Flow<List<AppUsage>> {
		return appUsageDao.getAppUsageByPackageName(packageName)
	}

	suspend fun insertOrUpdateAppUsage(appUsage: AppUsage) {
		val existingUsage =
			appUsageDao.getAppUsageByPackageAndDate(
				appUsage.packageName,
				LocalDate.now().toString()
			)
		if (existingUsage != null) {
			val updatedUsage = existingUsage.copy(
				scrollCount = existingUsage.scrollCount + appUsage.scrollCount,
				timeSpent = existingUsage.timeSpent + appUsage.timeSpent,
				appOpenCount = existingUsage.appOpenCount + appUsage.appOpenCount,
				scrollsBlocked = existingUsage.scrollsBlocked + appUsage.scrollsBlocked,
				updatedAt = System.currentTimeMillis()
			)
			appUsageDao.updateAppUsage(updatedUsage)
		} else {
			appUsageDao.insertAppUsage(appUsage)
		}
	}

}