<template>
  <view>
	  <!-- <view class="content">
	          <view class="avatorWrapper">
	              <view class="avator">
	                  <image class="img" src="../../static/login.png" mode="widthFix"></image>
	              </view>
	          </view>
	          <view class="form">
	              <view class="inputWrapper">
	                  <input class="input" type="text" v-model="loginForm.username" value="" placeholder="请输入用户名"/>
	              </view>
	              <view class="inputWrapper">
	                  <input class="input" type="password" v-model="loginForm.password" value="" placeholder="请输入密码"/>
	              </view>
				  <!-- <view class="inputWrapper">
					  <input v-model="loginForm.code" type="number" placeholder="请输入验证码" />
					  <image class="codeImg" :src="codeUrl" @click="getCode"></image>
				  </view> -->
	            <!--  <view class="loginBtn">
	                  <text class="btnValue" @click="submit">登录</text>
	              </view>
	          </view>
	      </view> --> 
   <view class="ui-all">
      <view class="ui-list">
        <text>账号</text>
        <input v-model="loginForm.username" placeholder="请输入账号" />
      </view>
      <view class="ui-list">
        <text>密码</text>
        <input v-model="loginForm.password" type="text" :password="isHidePassword ? true : false" placeholder="请输入密码" maxlength="20" />
        <image @click="isHidePasswordClick" class="eye" :src="isHidePassword ? '/static/img/attention.png' : '/static/img/attention_forbid.png'"></image>
      </view>
      <view class="ui-list" v-if="captchaOnOff">
        <text>验证码</text>
        <input v-model="loginForm.code" type="number" placeholder="请输入验证码" maxlength="20" />
        <image class="codeImg" :src="codeUrl" @click="getCode"></image>
      </view>
    </view>
    <button class="login mt5" @click="submit">登录</button>
  </view>
</template>
<script>
import { checkPwd, checkShenfenzheng } from '@/common/validate.js';
import { getCodeImg } from '@/api/login';
import { updateUser } from '@/api/user.js';
export default {
  data() {
    return {
      isHidePassword: true,
      codeUrl: '',
      captchaOnOff: true,
      loginForm: {
        username: this.$store.state.userInfo.userName,
        password: '',
        rememberMe: false,
        code: '',
        uuid: ''
      },
      register: false,
      userExtend2: ''
    };
  },
  onLoad() {
    // #ifdef APP-PLUS
    // 获取客户端标识
    let pinf = plus.push.getClientInfo();
    if (pinf && pinf.clientid) uni.setStorageSync('cid', pinf.clientid);
    else {
      var obtainingCIDTimer = setInterval(function() {
        pinf = plus.push.getClientInfo();
        if (pinf.clientid) {
          uni.setStorageSync('cid', pinf.clientid);
          clearInterval(obtainingCIDTimer);
        }
      }, 50);
    }
    setTimeout(() => {
      console.log('cid', uni.getStorageSync('cid'));
    }, 500);
    // #endif
    this.getCode();
  },
  methods: {
    isHidePasswordClick() {
      this.isHidePassword = !this.isHidePassword;
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = 'data:image/gif;base64,' + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    submit() {
      if (!this.loginForm.username) {
        this.toast('请填写账号');
        return;
      }
      if (!this.loginForm.password) {
        this.toast('请填写密码');
        return;
      }
      this.toast('登录中...');
      this.$store
        .dispatch('Login', this.loginForm)
        .then(() => {
          this.$store.dispatch('GetInfo').then(res => {
            let param = {
              userExtend2: uni.getStorageSync('cid')
            };
            updateUser(param).then(res => {
              uni.reLaunch({
                url: '/pages/index/index'
              });
            });
          });
        })
        .catch(() => {
          if (this.captchaOnOff) {
            this.getCode();
          }
        });
    }
  }
};
</script>
<!-- <style lang="scss">
.eye {
  position: absolute;
  height: 50rpx;
  width: 50rpx;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

.register {
  margin-top: 20rpx;
  color: #69c8fa;
  text-align: right;
}

.tishi {
  font-size: 20rpx;
  color: gray;
}

.login {
  width: 200rpx;
  color: white;
  height: 75rpx;
  font-size: 30rpx;
  background-color: #69c8fa;
}

.codeImg {
  height: 64rpx;
  position: absolute;
  right: 0;
  top: 10rpx;
  width: 200rpx;
}
</style>
 -->
 
 <style>
     .content {
         background: #377EB4;
         width: 100vw;
         height: 100vh;
     }
     .avatorWrapper{
         height: 30vh;
         width: 100vw;
         display: flex;
         justify-content: center;
         align-items: flex-end;
     }
     .avator{
         width: 200upx;
         height: 200upx;
         overflow: hidden;
     }
     .avator .img{
         width: 100%
     }
     .form{
         padding: 0 100upx;
         margin-top: 80px;
     }
     .inputWrapper{
         width: 100%;
         height: 80upx;
         background: white;
         border-radius: 20px;
         box-sizing: border-box;
         padding: 0 20px;
         margin-top: 25px;
     }
     .inputWrapper .input{
         width: 100%;
         height: 100%;
         text-align: center;
         font-size: 15px;
     }
     .loginBtn{
         width: 100%;
         height: 80upx;
         background: #77B307;
         border-radius: 50upx;
         margin-top: 50px;
         display: flex;
         justify-content: center;
         align-items: center;
         
     }
     .loginBtn .btnValue{
         color: white;
     }
     .forgotBtn{
         text-align: center;
         color: #EAF6F9;
         font-size: 15px;
         margin-top: 20px;
     }
 </style>