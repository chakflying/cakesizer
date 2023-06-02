package com.nelc.cakesizer.welcomeactivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelc.cakesizer.R
import com.nelc.cakesizer.ui.theme.CakeSizerTheme

@Composable
fun CreditsScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 24.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    text = stringResource(R.string.credits),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onClose()
                    }
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "Close Credits",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = "\"Birthday Cake\" by Sakthivel G is licensed under Creative Commons Attribution.",
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = "\"Cake Skanned 8\" by zamorev4d is licensed under Creative Commons Attribution.",
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = "\"Strawberry\" by gelmi.com.br is licensed under Creative Commons Attribution.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreditsScreenPreview() {
    CakeSizerTheme {
        CreditsScreen()
    }
}