package com.vishal2376.scrollblock.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishal2376.scrollblock.R
import com.vishal2376.scrollblock.domain.model.AppUsage
import com.vishal2376.scrollblock.domain.model.TimeWastedInfo
import com.vishal2376.scrollblock.presentation.analytics.components.SummaryItemComponent
import com.vishal2376.scrollblock.presentation.common.CustomPieChart
import com.vishal2376.scrollblock.presentation.common.descriptionStyle
import com.vishal2376.scrollblock.presentation.common.fontMontserrat
import com.vishal2376.scrollblock.presentation.common.h1style
import com.vishal2376.scrollblock.presentation.common.h2style
import com.vishal2376.scrollblock.presentation.home.components.PieChartIndicatorComponent
import com.vishal2376.scrollblock.ui.theme.ScrollBlockTheme
import com.vishal2376.scrollblock.ui.theme.instagramColor
import com.vishal2376.scrollblock.ui.theme.linkedinColor
import com.vishal2376.scrollblock.ui.theme.snapchatColor
import com.vishal2376.scrollblock.ui.theme.white
import com.vishal2376.scrollblock.ui.theme.youtubeColor
import com.vishal2376.scrollblock.utils.formatNumber
import com.vishal2376.scrollblock.utils.formatTime
import com.vishal2376.scrollblock.utils.getAppTimeSpent
import com.vishal2376.scrollblock.utils.instagramPackage
import com.vishal2376.scrollblock.utils.linkedinPackage
import com.vishal2376.scrollblock.utils.snapchatPackage
import com.vishal2376.scrollblock.utils.youtubePackage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticScreen(appUsage: List<AppUsage>, onBack: () -> Unit) {

	val totalTimeWasted = appUsage.sumOf { it.timeSpent }
	val totalScrollCount = appUsage.sumOf { it.scrollCount }
	val totalAppOpenCount = appUsage.sumOf { it.appOpenCount }
	val totalAppScrollBlocked = appUsage.sumOf { it.scrollsBlocked }

	val instagramTimeSpent = getAppTimeSpent(appUsage, instagramPackage)
	val youtubeTimeSpent = getAppTimeSpent(appUsage, youtubePackage)
	val linkedinTimeSpent = getAppTimeSpent(appUsage, linkedinPackage)
	val snapchatTimeSpent = getAppTimeSpent(appUsage, snapchatPackage)

	val timeWastedList = listOf(
		TimeWastedInfo("Instagram", instagramTimeSpent, instagramColor),
		TimeWastedInfo("Youtube", youtubeTimeSpent, youtubeColor),
		TimeWastedInfo("Linkedin", linkedinTimeSpent, linkedinColor),
		TimeWastedInfo("Snapchat", snapchatTimeSpent, snapchatColor)
	).filter { it.timeWasted > 0 }


	Scaffold(topBar = {
		TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
			title = {
				Text(
					text = "Analytics", style = h2style
				)
			},
			navigationIcon = {
				IconButton(onClick = {
					onBack()
				}) {
					Icon(
						imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
					)
				}
			})

	}) { innerPadding ->

		val blackGradient = Brush.verticalGradient(
			listOf(
				MaterialTheme.colorScheme.primary,
				MaterialTheme.colorScheme.secondary
			)
		)

		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.background(MaterialTheme.colorScheme.primary)
				.padding(innerPadding),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			Column(
				Modifier
					.fillMaxWidth()
					.height(350.dp)
					.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
					.background(blackGradient),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {

				// pie chart
				Box(
					modifier = Modifier
						.clip(CircleShape)
						.clickable {},
					contentAlignment = Alignment.Center
				) {
					Column(
						modifier = Modifier.padding(top = 8.dp),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "This Week",
							textAlign = TextAlign.Center,
							style = descriptionStyle
						)
						Text(
							text = formatTime(totalTimeWasted),
							textAlign = TextAlign.Center,
							fontSize = 25.sp,
							fontFamily = fontMontserrat,
						)
					}
					CustomPieChart(
						data = timeWastedList, pieChartSize = 190.dp
					)
				}

				Spacer(modifier = Modifier.height(16.dp))

				// pie chart indicator
				LazyVerticalGrid(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 32.dp),
					columns = GridCells.Fixed(2),
					verticalArrangement = Arrangement.spacedBy(8.dp),
					horizontalArrangement = Arrangement.spacedBy(24.dp),
				) {
					items(timeWastedList) {
						PieChartIndicatorComponent(
							appName = it.name,
							time = it.timeWasted,
							color = it.color
						)
					}
				}

			}

			Row(
				modifier = Modifier.padding(horizontal = 16.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Icon(
					imageVector = Icons.Default.BarChart,
					contentDescription = null,
					tint = white
				)
				Text(text = "Summary", style = h1style)
			}
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(8.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(
						8.dp,
						Alignment.CenterHorizontally
					),
					verticalAlignment = Alignment.CenterVertically
				) {
					SummaryItemComponent(
						title = "Time\nWasted",
						info = formatTime(totalTimeWasted),
						icon = R.drawable.clock,
						index = 1
					)
					SummaryItemComponent(
						title = "Total\nScrolls",
						info = formatNumber(totalScrollCount.toLong()),
						icon = R.drawable.scroll,
						index = 2
					)
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(
						8.dp,
						Alignment.CenterHorizontally
					),
					verticalAlignment = Alignment.CenterVertically
				) {
					SummaryItemComponent(
						title = "Scroll\nBlocked",
						info = formatNumber(totalAppScrollBlocked.toLong()),
						icon = R.drawable.shield,
						index = 3
					)
					SummaryItemComponent(
						title = "App\nOpened",
						info = formatNumber(totalAppOpenCount.toLong()),
						icon = R.drawable.dashboard,
						index = 4
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun AnalyticScreenPreview() {
	ScrollBlockTheme {
		AnalyticScreen(emptyList(), {})
	}
}