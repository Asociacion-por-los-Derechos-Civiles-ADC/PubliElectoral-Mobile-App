/*
 * Copyright 2018 Allan Wang
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pitchedapps.frost.activities

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import ca.allanwang.kau.utils.withMainContext
import com.google.android.material.tabs.TabLayout
import com.pitchedapps.frost.facebook.FbItem
import com.pitchedapps.frost.facebook.parsers.BadgeParser
import com.pitchedapps.frost.kotlin.subscribeDuringJob
import com.pitchedapps.frost.utils.L
import com.pitchedapps.frost.views.BadgedIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel

@UseExperimental(ExperimentalCoroutinesApi::class)
class MainActivity : BaseMainActivity() {

    override val fragmentChannel = BroadcastChannel<Int>(10)
    override val headerBadgeChannel = BroadcastChannel<String>(Channel.CONFLATED)

    override fun onNestedCreate(savedInstanceState: Bundle?) {
        with(contentBinding) {
            setupTabs()
            setupViewPager()
        }
    }

    private fun ActivityMainContentBinding.setupViewPager() {
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (lastPosition == position) {
                    return
                }
                if (lastPosition != -1) {
                    fragmentChannel.offer(-(lastPosition + 1))
                }
                fragmentChannel.offer(position)
                lastPosition = position
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                val delta = positionOffset * (SELECTED_TAB_ALPHA - UNSELECTED_TAB_ALPHA)
                tabsForEachView { tabPosition, view ->
                    view.setAllAlpha(
                        when (tabPosition) {
                            position -> SELECTED_TAB_ALPHA - delta
                            position + 1 -> UNSELECTED_TAB_ALPHA + delta
                            else -> UNSELECTED_TAB_ALPHA
                        }
                    )
                }
            }
        })
    }

    private fun ActivityMainContentBinding.setupTabs() {
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.ViewPagerOnTabSelectedListener(viewpager) {
            override fun onTabReselected(tab: TabLayout.Tab) {
                super.onTabReselected(tab)
                currentFragment?.onTabClick()
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                super.onTabSelected(tab)
                (tab.customView as BadgedIcon).badgeText = null
            }
        })
        headerBadgeChannel.subscribeDuringJob(this@MainActivity, Dispatchers.IO) { html ->
            val data =
                BadgeParser.parseFromData(cookie = fbCookie.webCookie, text = html)?.data
                    ?: return@subscribeDuringJob
            L.v { "Badges $data" }
            withMainContext {
                tabsForEachView { _, view ->
                    when (view.iicon) {
                        FbItem.FEED.icon -> view.badgeText = data.feed
                        FbItem.FRIENDS.icon -> view.badgeText = data.friends
                        FbItem.MESSAGES.icon -> view.badgeText = data.messages
                        FbItem.NOTIFICATIONS.icon -> view.badgeText = data.notifications
                    }
                }
            }
        }
    }
}
