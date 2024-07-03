package com.vishal2376.scrollblock.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vishal2376.scrollblock.R
import com.vishal2376.scrollblock.domain.model.AppUsage
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val instagramPackage = listOf(SupportedApps.Instagram.packageName)
val linkedinPackage = listOf(SupportedApps.Linkedin.packageName)
val snapchatPackage = listOf(SupportedApps.Snapchat.packageName)
val youtubePackage = listOf(
	SupportedApps.Youtube.packageName,
	SupportedApps.YoutubeRevanced.packageName,
	SupportedApps.YoutubeRevancedExtended.packageName
)

fun getAppTimeSpent(
	allAppUsage: List<AppUsage>, packageName: List<String>
): Int {
	return allAppUsage.filter { it.packageName in packageName }.sumOf { it.timeSpent }
}

fun getCurrentWeekDateRange(): Pair<String, String> {
	val today = LocalDate.now()
	val startOfWeek = today.with(DayOfWeek.MONDAY)
	val endOfWeek = today.with(DayOfWeek.SUNDAY)
	val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

	val startDate = startOfWeek.format(formatter)
	val endDate = endOfWeek.format(formatter)

	return Pair(startDate, endDate)
}

fun getCurrentMonthDateRange(): Pair<String, String> {
	val today = LocalDate.now()
	val startOfMonth = today.withDayOfMonth(1)
	val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())
	val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

	val startDate = startOfMonth.format(formatter)
	val endDate = endOfMonth.format(formatter)

	return Pair(startDate, endDate)
}

fun openMail(context: Context, title: String) {
	val subject = "${context.getString(R.string.app_name)}: $title"
	val uriBuilder = StringBuilder("mailto:" + Uri.encode(Constants.EMAIL))
	uriBuilder.append("?subject=" + Uri.encode(subject))
	val uriString = uriBuilder.toString()

	val intentTitle = "Send $title"
	val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
	context.startActivity(Intent.createChooser(intent, intentTitle))
}

fun openUrl(context: Context, urlString: String) {
	val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
	context.startActivity(intent)
}

fun shareApp(context: Context) {
	val shareIntent = Intent(Intent.ACTION_SEND)
	shareIntent.type = "text/plain"
	var shareMessage = context.getString(R.string.tag_line)
	shareMessage += Constants.REPO_URL + "\n\n"
	shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
	shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
	context.startActivity(Intent.createChooser(shareIntent, "Share This App"))
}

fun formatTime(seconds: Int): String {
	val hours = seconds / 3600
	val minutes = (seconds % 3600) / 60

	return when {
		hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
		hours > 0 -> "${hours}h"
		minutes > 0 -> "${minutes}m"
		else -> "${seconds}s"
	}
}

fun formatNumber(number: Long): String {
	return when {
		number >= 1_000_000_000 -> String.format(Locale.US, "%.1fB", number / 1_000_000_000.0)
		number >= 1_000_000 -> String.format(Locale.US, "%.1fM", number / 1_000_000.0)
		number >= 1_000 -> String.format(Locale.US, "%.1fK", number / 1_000.0)
		else -> number.toString()
	}.replace(".0", "")
}