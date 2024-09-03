import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.astragram.data.FavoriteImage

@Composable
fun FavoriteImageCard(
    favorite: FavoriteImage,
    onRemoveClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // Log the local path of the image to see if we get the correct path
        Log.d("FavoriteImageCard", "Loading image from path: ${favorite.localPath}")

        // Load the image from local path using Coil's painter
        Image(
            painter = rememberAsyncImagePainter(favorite.localPath),
            contentDescription = favorite.title,
            modifier = Modifier
                .size(128.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        IconButton(onClick = onRemoveClick) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Remove Favorite")
        }
    }
}
