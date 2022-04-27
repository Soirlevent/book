<template>
  <view class="container">
    <view class="ui-all">
      <!-- 用户信息头像修改等需要后台配合 或者如果能将原有的上传头像接口放进来也可以 问题出在文件可以上传但是不更新redis nickName没有存储在redis中 -->
      <!-- <view class="avatar" @tap="avatarChoose"> -->
      <view class="avatar">
        <view class="imgAvatar">
          <view class="iavatar" :style="'background: url(' +
        avatar+') no-repeat center/cover #eeeeee;'"></view>
        </view>
        <text class="xgzp"> 修改用户照片 </text>
      </view>
      <view class="ui-list right">
        <text>密码</text>
        <navigator class="continer" url="/pages/resetPwd/resetPwd" hover-class="none"><text id="xgmm"> 去修改 </text></navigator>
      </view>
      <button class="mySave" @tap="savaInfo">保存</button>
      <!-- <button size="mini" class="logOut" @tap="logOut">退出登录</button> -->
    </view>
  </view>
</template>
<script>
import config from '@/config'
import { commonUpload } from "@/api/business.js"
import { updateUser } from "@/api/user.js"
export default {
  data() {
    return {
      avatar: this.$store.state.userInfo.avatar,
      baseUrl: config.baseUrl,
    }
  },
  methods: {
    onLoad() {
      this.$store.dispatch('GetInfo')
        .then(res => {
          this.avatar = res.user.avatar
          this.index = res.user.sex
          this.nickName = res.user.nickName
        })
    },
    gotoResetPwd() {
      uni.push({
        url: '/pages/resetPwd/resetPwd'
      })
    },
    bindPickerChange(e) {
      this.index = e.target.value
    },
    avatarChoose() {
      let that = this;
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success(res) {
          that.imgUpload(res.tempFilePaths);
        }
      });
    },
    savaInfo() {
      let avatar = this.avatar.replace(this.baseUrl, '')
      let param = {
        userId: this.$store.state.userInfo.userId,
        avatar: avatar,
        nickName: this.nickName,
      }
      updateUser(param)
        .then(res => {
          this.toast("修改成功")
        })
    },
    imgUpload(file) {
      commonUpload(file[0])
        .then(res => {
          this.avatar = this.baseUrl + res.fileName
          let avatar = this.avatar.replace(this.baseUrl, '')
          let param = {
            userId: this.$store.state.userInfo.userId,
            avatar: avatar,
            nickName: this.nickName,
          }
        });
    },
    onNavigationBarButtonTap() {
      this.logOut()
    },
    logOut() {
      this.showConfirm("确认退出登录吗?")
        .then(res => {
          if (res.confirm) {
            this.$store.dispatch('LogOut')
              .then(res => {
                uni.reLaunch({
                  url: '/pages/login/login'
                })
              });
          }
        })
    }
  },
}

</script>
<style lang="scss">
.mySave {
  color: white;
  background-color: #69c8fa;
  top: 20rpx;
  height: 60rpx;
  line-height: 60rpx;
}

.logOut {
  color: white;
  background-color: #69c8fa;
  position: absolute;
  bottom: 40rpx;
  right: 50%;
  transform: translate(50%);
}

#xgmm {
  color: #69c8fa;
  margin-top: -10rpx;
  // line-height: 15rpx;
  // text-decoration: underline;
}

</style>
