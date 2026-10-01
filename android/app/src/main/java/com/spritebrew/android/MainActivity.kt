package com.spritebrew.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF14120F)
private val SurfaceDark = Color(0xFF201D19)
private val SurfaceRaised = Color(0xFF29251F)
private val Amber = Color(0xFFF5B942)
private val Cream = Color(0xFFF2EBDD)
private val Muted = Color(0xFFAAA398)
private val Border = Color(0xFF403A31)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Amber,
                    onPrimary = Ink,
                    background = Ink,
                    onBackground = Cream,
                    surface = SurfaceDark,
                    onSurface = Cream,
                    outline = Border,
                ),
            ) {
                SpriteBrewApp()
            }
        }
    }
}

private enum class AppTab(val title: String, val icon: String) {
    Brew("Brew", "✦"),
    Tools("Tools", "▦"),
    Gallery("Gallery", "▣"),
    Account("Account", "●"),
}

@Composable
private fun SpriteBrewApp() {
    var selectedTab by rememberSaveable { mutableStateOf(AppTab.Brew) }

    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceDark,
                contentColor = Cream,
            ) {
                AppTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(tab.icon, fontSize = 18.sp) },
                        label = {
                            Text(
                                tab.title,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Amber,
                            selectedTextColor = Amber,
                            indicatorColor = Amber.copy(alpha = 0.12f),
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted,
                        ),
                    )
                }
            }
        },
    ) { innerPadding ->
        when (selectedTab) {
            AppTab.Brew -> BrewScreen(innerPadding)
            AppTab.Tools -> ToolsScreen(innerPadding)
            AppTab.Gallery -> GalleryScreen(innerPadding)
            AppTab.Account -> AccountScreen(innerPadding)
        }
    }
}

@Composable
private fun BrewScreen(padding: PaddingValues) {
    var prompt by rememberSaveable { mutableStateOf("") }
    var selectedMode by rememberSaveable { mutableStateOf("Create new") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        BrandHeader()
        Text("AI SPRITE GENERATOR", style = Eyebrow, color = Amber)
        Text(
            "Bring a character\nto pixel life.",
            fontFamily = FontFamily.Monospace,
            fontSize = 25.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Cream,
        )
        Text(
            "Describe your idea, choose a style, and brew a game-ready sprite sheet.",
            style = MaterialTheme.typography.bodyMedium,
            color = Muted,
        )

        NativeNotice(
            title = "Native app foundation",
            description = "This Android project is being built as a fully native Kotlin app. Sign-in, generation, and the sprite tools are not connected yet.",
        )

        SectionLabel("CHOOSE A WORKFLOW")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("Create new", "Animate art").forEach { mode ->
                ChoiceChip(
                    text = mode,
                    selected = selectedMode == mode,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedMode = mode },
                )
            }
        }

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(if (selectedMode == "Create new") "Character description" else "Animation idea") },
            placeholder = { Text("A tiny forest mage with a glowing lantern…") },
            minLines = 4,
            shape = RoundedCornerShape(12.dp),
        )

        Surface(
            color = SurfaceDark,
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SectionLabel("STYLE")
                Text("Choose from 21 pixel art styles", color = Cream, fontSize = 14.sp)
                Text("Fast, Plus, Pro, and animation styles", color = Muted, fontSize = 12.sp)
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Amber,
                contentColor = Ink,
                disabledContainerColor = Amber.copy(alpha = 0.45f),
                disabledContentColor = Ink.copy(alpha = 0.7f),
            ),
            enabled = false,
        ) {
            Text("BREW SPRITE", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        Text(
            "Generation will be enabled after native account and API integration.",
            color = Muted,
            fontSize = 11.sp,
        )
    }
}

@Composable
private fun ToolsScreen(padding: PaddingValues) {
    var selectedTool by rememberSaveable { mutableStateOf<String?>(null) }
    val tools = listOf(
        "Upload & Slice" to "Split a sprite sheet into individual frames.",
        "Animation Preview" to "Play frames with touch-friendly controls.",
        "Pixel Editor" to "Touch up individual pixels and colors.",
        "Image Resizer" to "Resize art with crisp nearest-neighbor scaling.",
        "Multi-Engine Export" to "Prepare sheets for popular game engines.",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BrandHeader()
        Text("SPRITE WORKSHOP", style = Eyebrow, color = Amber)
        Text("Your tools, rebuilt for touch.", style = MaterialTheme.typography.headlineSmall, color = Cream)
        Text(
            "The web app's free sprite tools are planned for native Android.",
            color = Muted,
            style = MaterialTheme.typography.bodyMedium,
        )
        tools.forEach { (title, description) ->
            ToolCard(
                title = title,
                description = description,
                selected = selectedTool == title,
                onClick = { selectedTool = title },
            )
        }
        selectedTool?.let {
            NativeNotice(
                title = "$it is in the native port queue",
                description = "This feature is not implemented in the Android app yet.",
            )
        }
    }
}

@Composable
private fun GalleryScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrandHeader()
        Text("YOUR LIBRARY", style = Eyebrow, color = Amber)
        Text("A home for every brew.", style = MaterialTheme.typography.headlineSmall, color = Cream)
        NativeNotice(
            title = "Gallery is not connected yet",
            description = "Your SpriteBrew generations will appear here after native sign-in and gallery sync are implemented.",
        )
    }
}

@Composable
private fun AccountScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrandHeader()
        Text("ACCOUNT", style = Eyebrow, color = Amber)
        Text("Your workshop account.", style = MaterialTheme.typography.headlineSmall, color = Cream)
        NativeNotice(
            title = "Sign-in is not connected yet",
            description = "The native app will use SpriteBrew's existing Clerk account and token balance.",
        )
        ToolCard(
            title = "Tokens and rewards",
            description = "Daily login rewards and token pack checkout will be added with account support.",
            selected = false,
            onClick = {},
        )
    }
}

@Composable
private fun BrandHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(Amber, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text("S", color = Ink, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = 22.sp)
        }
        Spacer(Modifier.width(11.dp))
        Column {
            Text("SPRITEBREW", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = Cream, fontSize = 13.sp)
            Text("PIXEL ART WORKSHOP", fontFamily = FontFamily.Monospace, color = Muted, fontSize = 9.sp)
        }
        Spacer(Modifier.weight(1f))
        Text("NATIVE BETA", fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Amber)
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 1.sp,
        color = Muted,
    )
}

private val Eyebrow
    @Composable get() = MaterialTheme.typography.labelSmall.copy(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
    )

@Composable
private fun ChoiceChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = if (selected) Amber.copy(alpha = 0.14f) else SurfaceDark,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) Amber else Border,
        ),
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 13.dp),
            color = if (selected) Amber else Muted,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun ToolCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        color = if (selected) SurfaceRaised else SurfaceDark,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) Amber.copy(alpha = 0.7f) else Border),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(38.dp).background(Amber.copy(alpha = 0.12f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text("✦", color = Amber, fontSize = 16.sp)
            }
            Spacer(Modifier.width(13.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, color = Cream, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(description, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
            }
            Text("›", color = Amber, fontSize = 24.sp)
        }
    }
}

@Composable
private fun NativeNotice(title: String, description: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Amber.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Amber.copy(alpha = 0.25f)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(title, color = Amber, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(description, color = Cream.copy(alpha = 0.78f), fontSize = 12.sp, lineHeight = 18.sp)
        }
    }
}
