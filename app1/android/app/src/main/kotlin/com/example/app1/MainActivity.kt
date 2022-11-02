package com.example.app1

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {

    private val CHANNEL = "intent.open.app2"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // This method is invoked on the main thread.
            call, result ->
            if (call.method == "openApp2") {
                val hasArguments = call.hasArgument("contador")
                if (hasArguments) {
                    Log.d("TAG", "Chamando app2")
                    openApp("com.example.app2", "false", call.argument<String>("contador"))
                }
            } else {
                result.notImplemented()
            }
        }

    }


    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (ignored: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openApp(packageName: String, openStore: String, argument: String?): String? {
        if (isAppInstalled(packageName)) {
            Log.d("TAG", "app2 encontrado")


            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                Log.d("TAG", "Enviando o contador: "+argument)
                launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                launchIntent.action = Intent.ACTION_SEND
                launchIntent.putExtra("my_text", argument)
                launchIntent.type = "text/plain"
                context.startActivity(launchIntent)
                return "app_opened"
            }
        } else {
            Log.d("TAG", "app2 NAO encontrado")
            if (openStore !== "false") {
                val intent1 = Intent(Intent.ACTION_VIEW)
                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent1.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                context.startActivity(intent1)
                return "navigated_to_store"
            }
        }
        return "something went wrong"
    }

}
