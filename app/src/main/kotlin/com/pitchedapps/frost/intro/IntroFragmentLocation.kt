/*
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
package com.pitchedapps.frost.intro

import android.os.Bundle
import android.view.View
import ca.allanwang.kau.utils.scaleXY
import com.pitchedapps.frost.R
import com.pitchedapps.frost.activities.IntroActivity
import com.pitchedapps.frost.databinding.IntroLocationBinding
import com.pitchedapps.frost.enums.Location
import ca.allanwang.kau.utils.string


class IntroFragmentLocation : BaseIntroFragment(R.layout.intro_location) {

    private lateinit var binding: IntroLocationBinding

    val locationList
        get() = with(binding) {
            listOf(argentina, chile, mexico, paraguay)
        }

    override fun viewArray(): Array<Array<out View>> = with(binding) {
        arrayOf(
            arrayOf(title),
            arrayOf(argentina, chile),
            arrayOf(mexico, paraguay)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IntroLocationBinding.bind(view)
        binding.init()
    }

    private fun IntroLocationBinding.init() {
        argentina.setLocationClick(Location.ARGENTINA)
        chile.setLocationClick(Location.CHILE)
        mexico.setLocationClick(Location.MEXICO)
        paraguay.setLocationClick(Location.PARAGUAY)
        val currentLocation = prefs.location
        if (currentLocation in 0..3)
            locationList.forEachIndexed { index, v ->
                v.scaleXY = if (index == currentLocation) 1.6f else 0.8f
            }
    }

    private fun View.setLocationClick(location: Location) {
        setOnClickListener { v ->
            prefs.location = location.ordinal
            val name = Location(location.ordinal).name
            prefs.locationName = Location(location.ordinal).name
            locationList.forEach { it.animate().scaleXY(if (it == this) 1.6f else 0.8f).start() }
        }
    }
}
