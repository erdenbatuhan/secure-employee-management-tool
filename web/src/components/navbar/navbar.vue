<template>
  <div>
    <b-navbar class="navbar" toggleable="md" type="light" variant="light">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

      <b-navbar-brand>
        <a class="nav-item" v-on:click="redirectTo('/')"><img src="/static/images/logo.png" height="50" width="52" alt="Logo"/></a>
      </b-navbar-brand>

      <!-- Left-justified nav items -->
      <b-collapse is-nav id="nav_collapse">
        <b-navbar-nav class="navbar-nav-left">
            <b-nav-item><a v-on:click="redirectTo('/dashboard')"> Dashboard </a></b-nav-item>
            <!-- <b-nav-item><a v-on:click="redirectTo('/our-games')"> Our Games </a></b-nav-item> -->
            <b-nav-item><a v-on:click="redirectTo('/contact-us')"> Contact Us </a></b-nav-item>
            <b-nav-item><a v-on:click="redirectTo('/poll/' + currentPoll)"> Poll </a></b-nav-item>
        </b-navbar-nav>

        <!-- Right-justified nav items -->
        <b-navbar-nav class="ml-auto">
          <div class="navbar-collapse collapse w-100 order-3 dual-collapse2" v-if="signedInEmployeeId">
            <ul class="navbar-nav ml-auto">
              <li v-if="canView('IN')" class="nav-item">
                <b-button variant="outline-success"
                          class="btn-md vouch-buttons"
                          @click="showModal('IN')">
                  Vouch IN
                </b-button>
              </li>
              <li v-if="canView('OUT')" class="nav-item">
                <b-button variant="outline-danger"
                          class="btn-md vouch-buttons"
                          @click="showModal('OUT')">
                  Vouch OUT
                </b-button>
              </li>
              <li>
                <a class="nav-item" v-on:click="redirectToEmployeeProfile()">
                  <img :src="getImageSource(EMPLOYEE_IMAGE_DIR + signedInEmployeeId)" class="navbar-img" alt="">
                </a>
              </li>
            </ul>
          </div>
          <b-nav-item v-else right>
            <b-button variant="outline-success"
                      @click="showModal(null)">
              Sign in
            </b-button>
          </b-nav-item>
        </b-navbar-nav>

        <pass-auth ref="passAuth"></pass-auth>
      </b-collapse>
    </b-navbar>

    <div v-if="bottomPadding">
      <br><br>
    </div>
  </div>
</template>

<script>
  import CommonMixin from '../mixins/common-mixin.js'
  import passAuth from "./pass-auth.vue"

  export default {
    mixins: [CommonMixin],
    components: {passAuth},
    props: ['bottomPadding', 'employeeId', 'voucherItemDtoListLength'],
    data() {
      return {
        EMPLOYEE_IMAGE_DIR: 'employee/',
        LOGO_PATH: '/static/images/navbar/logo.png',
        name: 'navbar',
        currentPoll: 'jelly'
      }
    },
    computed: {
      signedInEmployeeId () {
        return (this.$cookies.isKey('signedInEmployeeId')) ? this.$cookies.get('signedInEmployeeId') : null
      }
    },
    methods: {
      showModal: function (voucherType) {
        this.$refs.passAuth.showModal(voucherType)
      },
      canView: function (voucherType) {
        let isSignedInEmployeeProfile = this.employeeId === this.signedInEmployeeId

        let isvoucherTypeIn = (voucherType === 'IN') && this.voucherItemDtoListLength % 2 === 0
        let isvoucherTypeOut = (voucherType === 'OUT') && this.voucherItemDtoListLength % 2 !== 0

        return isSignedInEmployeeProfile && (isvoucherTypeIn || isvoucherTypeOut)
      }
    }
  }
</script>

<style scoped>
  .navbar {
    min-height: 86px;
  }

  .navbar-img {
    height: auto;
    width: auto;
    max-height: 70px;
    max-width: 250px;
    border-radius: 50%;
  }
  
  .nav-item {
    cursor: pointer;
  }

  .vouch-buttons {
    margin-top: 15px;
    margin-right: 20px;
  }
</style>
