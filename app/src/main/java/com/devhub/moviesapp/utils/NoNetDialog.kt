import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devhub.moviesapp.R

@Composable
fun NoInternetDialog(
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(id = R.string.no_net_check))
        },
        text = {
            Text(stringResource(id = R.string.continue_local_data))
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(id = R.string.ok))
            }
        },

    )
}

