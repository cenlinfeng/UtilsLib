# UtilsLib

    allprojects {
    
        repositories {
            ...
            maven { url 'https://jitpack.io' }
    
        }
    }


    dependencies {
    
        implementation 'com.github.cenlinfeng:UtilsLib:0.101'
    
    }  

## 1 UI类
  
    简单的LoadingView加载框
  
    //显示加载框

    LoadingView.getInstance().showLoading("正在加载...");
  
    //关闭加载框

    LoadingView.getInstance().hideLoading();

## 2 布局类

    增加流式布局，属性只有flow_gravity (left,center,right)

## 3 工具类

### 1.Toast工具类

        ToastUtils.showToast(context, "测试吐司");

        或者

        ToastUtils.showToast(this, "测试吐司", Toast.LENGTH_LONG);

### 2.Base64图片和编码工具类 Base64Utils

### 3.应用工具类 AppUtils

### 4.图片选择器（初步完成） 

### 5.LRUCache缓存


