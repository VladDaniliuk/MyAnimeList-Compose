package shov.studio.ui.anime.ui.addtolist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastRoundToInt
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.Status
import shov.studio.ui.anime.R
import core.ui.common.modifiers.placeholder

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AddToListView(
    event: (AddToListContract.Event) -> Unit,
    state: AddToListContract.State,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .imePadding(),
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(R.string.list_status)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { event(AddToListContract.Event.OnCancelClick) },
                            enabled = state.isClickable
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { event(AddToListContract.Event.OnConfirmClick) },
                            enabled = state.isClickable
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Done,
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = topAppBarScrollBehavior
                )

                AnimatedVisibility(visible = state.isUpdating) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.tags)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(end = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(state.tags) { index, tag ->
                        var clicked by remember { mutableStateOf(false) }

                        InputChip(
                            selected = true,
                            onClick = {
                                if (clicked) {
                                    clicked = false
                                    event(AddToListContract.Event.OnTagDeleteClick(index))
                                } else clicked = true
                            },
                            label = { Text(text = tag) },
                            trailingIcon = {
                                if (clicked) {
                                    Icon(
                                        imageVector = Icons.Rounded.DeleteOutline,
                                        tint = MaterialTheme.colorScheme.error,
                                        contentDescription = null
                                    )
                                } else Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    }

                    if (state.isLoading) {
                        items(
                            count = 5,
                            key = Int::toString,
                            contentType = Int::toString
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(height = 32.dp, width = 64.dp)
                                    .placeholder(enabled = true)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { event(AddToListContract.Event.OnTagAddClick) },
                    enabled = state.isClickable
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }

            AnimatedVisibility(visible = state.isAddTagOpen) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.tag,
                        onValueChange = { value -> event(AddToListContract.Event.OnTagChange(value)) },
                        placeholder = { Text(stringResource(R.string.add_tag)) },
                        label = { Text(stringResource(R.string.add_tag)) }
                    )

                    Button(onClick = { event(AddToListContract.Event.OnTagSaveClick) }) {
                        Text(stringResource(R.string.add))
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = state.isStatusMenuOpen,
                    onExpandedChange = { isExpanded ->
                        if (state.isClickable) event(
                            AddToListContract.Event.OnStatusOpenChange(
                                isExpanded
                            )
                        )
                    },
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        enabled = state.isLoading.not(),
                        value = state.userStatus?.id?.let { id -> stringResource(id) }
                            ?: "",
                        onValueChange = { },
                        label = { Text(text = stringResource(R.string.status)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                state.isStatusMenuOpen
                            )
                        },
                    )

                    ExposedDropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = state.isStatusMenuOpen,
                        onDismissRequest = { event(AddToListContract.Event.OnStatusOpenChange(false)) },
                    ) {
                        val statuses = if (state.isAnime) persistentListOf(
                            Status.delete, Status.not_watching, Status.watching,
                            Status.completed, Status.on_hold, Status.dropped,
                            Status.plan_to_watch
                        ) else persistentListOf(
                            Status.delete, Status.not_reading, Status.reading,
                            Status.completed, Status.on_hold, Status.dropped,
                            Status.plan_to_read
                        )

                        statuses.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = stringResource(status.id)) },
                                onClick = { event(AddToListContract.Event.OnStatusClick(status)) },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }
            }

            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge,
                    text = "Rewatching"
                )

                Switch(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    checked = state.isRewatching,
                    onCheckedChange = { isRewatching ->
                        event(AddToListContract.Event.OnRewatchingClick(isRewatching))
                    },
                    enabled = state.isClickable
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                    text = "Score: "
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(enabled = state.isLoading),
                    style = MaterialTheme.typography.titleLarge,
                    text = state.score.fastRoundToInt().toString(),
                    textAlign = TextAlign.Start
                )
            }

            Slider(
                value = state.score,
                onValueChange = { score -> event(AddToListContract.Event.OnScoreChange(score)) },
                steps = 9,
                valueRange = 0f..10f,
                enabled = state.isClickable
            )

            val focusManager = LocalFocusManager.current

            if (state.isAnime) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.episodes?.toString() ?: "",
                    onValueChange = { episodes ->
                        event(AddToListContract.Event.OnEpisodesChange(episodes))
                    },
                    placeholder = { Text(text = stringResource(R.string._0)) },
                    label = { Text(text = stringResource(R.string.episodes_title)) },
                    readOnly = state.isClickable.not() and state.isEpisodesEditable.not(),
                    enabled = state.isLoading.not(),
                    suffix = {
                        state.maxEpisodes?.let { maxEpisodes ->
                            Text(
                                text = stringResource(
                                    R.string.per_value,
                                    maxEpisodes
                                )
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }),
                )
            } else {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.chapters.toString(),
                        onValueChange = { chapters ->
                            event(AddToListContract.Event.OnChaptersChange(chapters))
                        },
                        placeholder = { Text(text = stringResource(R.string._0)) },
                        readOnly = state.isClickable.not() and state.isChaptersEditable.not(),
                        label = { Text(text = stringResource(R.string.chapters_title)) },
                        suffix = {
                            state.maxChapters?.let { maxChapters ->
                                Text(
                                    text = stringResource(
                                        R.string.per_value,
                                        maxChapters
                                    )
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                    )


                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.volumes.toString(),
                        onValueChange = { volumes ->
                            event(AddToListContract.Event.OnVolumesChange(volumes))
                        },
                        placeholder = { Text(text = stringResource(R.string._0)) },
                        readOnly = state.isClickable.not() and state.isVolumesEditable.not(),
                        label = { Text(text = stringResource(R.string.volumes_title)) },
                        suffix = {
                            state.maxVolumes?.let { maxVolumes ->
                                Text(
                                    text = stringResource(
                                        R.string.per_value,
                                        maxVolumes
                                    )
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                    )
                }
            }

            TextField(
                value = state.comment,
                onValueChange = { comment -> event(AddToListContract.Event.OnCommentChange(comment)) },
                shape = MaterialTheme.shapes.medium,
                readOnly = state.isClickable.not(),
                enabled = state.isLoading.not(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                label = { Text(text = stringResource(R.string.comment)) },
                minLines = 3,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            AnimatedVisibility(visible = state.isUpdateError) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ErrorOutline,
                            contentDescription = null
                        )

                        state.error?.let { error -> Text(text = error) }
                    }
                }
            }
        }
    }
}
