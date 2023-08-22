import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.random.Random

fun uploadFileToFirebaseStorage(filePath: Uri, callback: (String) -> Unit) {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference

    val fileRef: StorageReference = storageRef.child("uploads/${Random.nextLong(100000000000,999999999999)}")

    fileRef.putFile(filePath)
        .addOnSuccessListener {
            // File successfully uploaded
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                callback(downloadUrl)
            }
        }
        .addOnFailureListener { e ->
            // Handle upload failure
            e.printStackTrace()
            callback("")
        }
}
