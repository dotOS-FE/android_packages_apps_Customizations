/*
 * Copyright (C) 2020 The dotOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.dotextras.custom.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.settings.dotextras.R
import com.android.settings.dotextras.custom.FeatureActivityBase
import com.android.settings.dotextras.custom.sections.cards.ContextCards
import com.android.settings.dotextras.custom.sections.cards.ContextCardsAdapter.Type.SECURE
import com.android.settings.dotextras.custom.sections.cards.ContextCardsAdapter.Type.SYSTEM
import com.android.settings.dotextras.custom.utils.ResourceHelper
import com.android.settings.dotextras.custom.views.NotSupportedView

class AODLockscreenSection : GenericSection() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.section_aod_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as FeatureActivityBase).setTitle(getString(R.string.section_aod_title))
        val optionsList = ArrayList<ContextCards>()
        val options2List = ArrayList<ContextCards>()
        if (ResourceHelper.hasAmbient(requireContext())) {
            featureManager.Secure().enableDozeIfNeeded(requireContext())
            buildSwitch(
                optionsList,
                iconID = R.drawable.ic_aod,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.doze_on_charge_title),
                accentColor = R.color.purple_500,
                feature = featureManager.Secure().DOZE_ON_CHARGE,
                featureType = SECURE,
                summary = getString(R.string.doze_on_charge_summary)
            )
	    buildSwitch(
                optionsList,
                iconID = R.drawable.ic_aod,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.aod_title),
                accentColor = R.color.orange_500,
                feature = featureManager.Secure().DOZE_ALWAYS_ON,
                featureType = SECURE,
                summary = getString(R.string.aod_summary)
            )
            buildSwitch(
                optionsList,
                iconID = R.drawable.ic_light_pulse,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.edge_lightning_title),
                accentColor = R.color.blue_900,
                feature = featureManager.System().AMBIENT_NOTIFICATION_LIGHT,
                featureType = SYSTEM,
                summary = getString(R.string.edge_lightning_summary)
            )
            buildSwipeable(
                optionsList,
                iconID = R.drawable.ic_light_pulse,
                subtitle = getString(R.string.edge_lightning_title),
                accentColor = R.color.teal_500,
                feature = featureManager.System().AMBIENT_NOTIFICATION_LIGHT_MODE,
                featureType = SYSTEM,
                min = 0,
                max = 3,
                default = 1,
                summary = getString(R.string.edge_lightning_summary_colormode),
                extraTitle = getString(R.string.style)
            ) { position, title ->
                run {
                    var newTitle = ""
                    when (position) {
                        0 -> newTitle = getString(R.string.edge_lightning_mode_default)
                        1 -> newTitle = getString(R.string.edge_lightning_mode_accent)
                        2 -> newTitle = getString(R.string.edge_lightning_mode_custom)
                        3 -> newTitle = getString(R.string.edge_lightning_mode_auto)
                    }
                    title.text = newTitle
                }
            }
            buildRGB(
                optionsList,
                iconID = R.drawable.ic_light_pulse,
                subtitle = getString(R.string.edge_lightning_title),
                feature = featureManager.System().AMBIENT_NOTIFICATION_LIGHT_COLOR,
                featureType = SYSTEM,
                summary = getString(R.string.edge_lightning_summary_color),
                defaultColor = resources.getColor(R.color.defaultEdgeLightningColor, null)
            ) {}
            setupLayout(optionsList, R.id.aodlockContextSection)
        } else {
            view.findViewById<NotSupportedView>(R.id.aodNS).visibility = View.VISIBLE
        }
	buildSwitch(
                optionsList,
                iconID = R.drawable.ic_aod,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.lockscreen_status_bar_title),
                accentColor = R.color.dot_red,
                feature = featureManager.System().LOCKSCREEN_STATUS_BAR,
                featureType = SYSTEM,
                summary = getString(R.string.lockscreen_status_bar_summary),
		enabled = true
            )
	buildSwitch(
                optionsList,
                iconID = R.drawable.ic_lock,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.fingerprint_success_vib_title),
                accentColor = R.color.dot_teal,
                feature = featureManager.System().FINGERPRINT_SUCCESS_VIB,
                featureType = SYSTEM,
                summary = getString(R.string.fingerprint_success_vib_summary),
                enabled = true
            )
	buildSwitch(
                optionsList,
                iconID = R.drawable.ic_lock,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.fingerprint_error_vib_title),
                accentColor = R.color.dot_violet,
                feature = featureManager.System().FINGERPRINT_ERROR_VIB,
                featureType = SYSTEM,
                summary = getString(R.string.fingerprint_error_vib_summary),
                enabled = true
            )
	buildSwitch(
                optionsList,
                iconID = R.drawable.ic_lock,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.lockscreen_lock_icon_title),
                accentColor = R.color.red_500,
                feature = featureManager.System().LOCKSCREEN_LOCK_ICON,
                featureType = SYSTEM,
                summary = getString(R.string.lockscreen_lock_icon_summary),
                enabled = true
            )
	buildSwitch(
                optionsList,
                iconID = R.drawable.ic_lock_outline,
                title = getString(R.string.disabled),
                subtitle = getString(R.string.lockscreen_hide_shortcuts_title),
                accentColor = R.color.dot_green,
                feature = featureManager.System().HIDE_LOCK_SHORTCUTS,
                featureType = SYSTEM,
                summary = getString(R.string.lockscreen_hide_shortcuts_summary),
            )
	buildSwipeable(
            optionsList,
            iconID = R.drawable.ic_blur,
            subtitle = getString(R.string.lockscreen_blur_title),
            accentColor = R.color.orange_500,
            feature = featureManager.System().LOCKSCREEN_BLUR,
            featureType = SYSTEM,
            min = 0,
            max = 100,
            default = 0,
	    summary = getString(R.string.lockscreen_blur_summary)
        )
        buildSwipeable(
            options2List,
            iconID = R.drawable.ic_screenoff,
            subtitle = getString(R.string.screen_off_animation_title),
            accentColor = R.color.purple_500,
            feature = featureManager.System().SCREEN_OFF_ANIMATION,
            featureType = SYSTEM,
            min = 0,
            max = 2,
            default = 0,
            summary = getString(R.string.screen_off_animation_summary),
            extraTitle = ""
        ) { position, title ->
            run {
                var newTitle = ""
                when (position) {
                    0 -> newTitle = getString(R.string.screen_off_animation_default)
                    1 -> newTitle = getString(R.string.screen_off_animation_crt)
                    2 -> newTitle = getString(R.string.screen_off_animation_scale)
                }
                title.text = newTitle
            }
        }
        setupLayout(options2List, R.id.aodlock2ContextSection)
    }

}
