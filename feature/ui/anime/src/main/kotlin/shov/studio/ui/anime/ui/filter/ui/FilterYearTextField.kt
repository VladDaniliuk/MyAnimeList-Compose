package shov.studio.ui.anime.ui.filter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R

@Composable
internal fun YearFilterTextField(
    year: String,
    setYear: (String) -> Unit,
    onYearSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = year,
        onValueChange = setYear,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { onYearSelected() }),
        label = { Text(text = stringResource(R.string.year)) },
        placeholder = { Text(text = stringResource(R.string.year)) },
        trailingIcon = if (year.isNotEmpty()) {
            {
                IconButton(onClick = { setYear("") }) {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                }
            }
        } else null,
        singleLine = true
    )
}

@Preview
@Composable
private fun YearFilterTextFieldPreview() {
    YearFilterTextField(year = "2022", setYear = {}, onYearSelected = {})
}
