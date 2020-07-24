/*
 * Copyright 2020 The Android Open Source Project
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

package androidx.compose.ui.focus

import androidx.compose.foundation.Box
import androidx.compose.ui.FocusModifier2
import androidx.test.filters.SmallTest
import androidx.compose.ui.Modifier
import androidx.compose.ui.focusObserver
import androidx.compose.ui.focusRequester
import androidx.ui.test.createComposeRule
import androidx.ui.test.runOnIdle
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@SmallTest
@OptIn(ExperimentalFocus::class)
@RunWith(JUnit4::class)
class CaptureFocusTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun active_captureFocus_changesStateToCaptured() {
        // Arrange.
        lateinit var focusState: FocusState2
        val focusRequester = FocusRequester()
        composeTestRule.setFocusableContent {
            Box(
                modifier = Modifier
                    .focusObserver { focusState = it }
                    .focusRequester(focusRequester)
                    .then(FocusModifier2(FocusState2.Active))
            )
        }

        // Act.
        val success = runOnIdle {
            focusRequester.captureFocus()
        }

        // Assert.
        runOnIdle {
            assertThat(success).isTrue()
            assertThat(focusState).isEqualTo(FocusState2.Captured)
        }
    }

    @Test
    fun activeParent_captureFocus_retainsStateAsActiveParent() {
        // Arrange.
        var focusState: FocusState2 = FocusState2.ActiveParent
        val focusRequester = FocusRequester()
        composeTestRule.setFocusableContent {
            Box(
                modifier = Modifier
                    .focusObserver { focusState = it }
                    .focusRequester(focusRequester)
                    .then(FocusModifier2(focusState))
            )
        }

        // Act.
        val success = runOnIdle {
            focusRequester.captureFocus()
        }

        // Assert.
        runOnIdle {
            assertThat(success).isFalse()
            assertThat(focusState).isEqualTo(FocusState2.ActiveParent)
        }
    }

    @Test
    fun captured_captureFocus_retainsStateAsCaptured() {
        // Arrange.
        var focusState = FocusState2.Captured
        val focusRequester = FocusRequester()
        composeTestRule.setFocusableContent {
            Box(
                modifier = Modifier
                    .focusObserver { focusState = it }
                    .focusRequester(focusRequester)
                    .then(FocusModifier2(focusState)) )
        }

        // Act.
        val success = runOnIdle {
            focusRequester.captureFocus()
        }

        // Assert.
        runOnIdle {
            assertThat(success).isTrue()
            assertThat(focusState).isEqualTo(FocusState2.Captured)
        }
    }

    @Test
    fun disabled_captureFocus_retainsStateAsDisabled() {
        // Arrange.
        var focusState = FocusState2.Disabled
        val focusRequester = FocusRequester()
        composeTestRule.setFocusableContent {
            Box(
                modifier = Modifier
                    .focusObserver { focusState = it }
                    .focusRequester(focusRequester)
                    .then(FocusModifier2(focusState)) )
        }

        // Act.
        val success = runOnIdle {
            focusRequester.captureFocus()
        }

        // Assert.
        runOnIdle {
            assertThat(success).isFalse()
            assertThat(focusState).isEqualTo(FocusState2.Disabled)
        }
    }

    @Test
    fun inactive_captureFocus_retainsStateAsInactive() {
        // Arrange.
        var focusState = FocusState2.Inactive
        val focusRequester = FocusRequester()
        composeTestRule.setFocusableContent {
            Box(
                modifier = Modifier
                    .focusObserver { focusState = it }
                    .focusRequester(focusRequester)
                    .then(FocusModifier2(focusState)) )
        }

        // Act.
        val success = runOnIdle {
            focusRequester.captureFocus()
        }

        // Assert.
        runOnIdle {
            assertThat(success).isFalse()
            assertThat(focusState).isEqualTo(FocusState2.Inactive)
        }
    }
}
