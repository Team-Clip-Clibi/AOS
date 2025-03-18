package com.example.signup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R

@Composable
fun CustomCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier,
    onCheckChange: (Boolean) -> Unit,
    isIconShow : Boolean = true
) {
    Row(
        modifier = modifier.clickable { onCheckChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularCheckBox(
            checked = checked,
            onCheckedChange = onCheckChange
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        if(isIconShow){
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "자세히 보기",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun CircularCheckBox(
    checked : Boolean,
    onCheckedChange : (Boolean) -> Unit
){
    Box(
        modifier = Modifier.size(40.dp)
            .background(
                color = if(checked) colorResource(id = R.color.purple) else colorResource(R.color.light_gray),
                shape = CircleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ){
        if(checked){
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "동의항목",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }else{
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "비동의항목",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
