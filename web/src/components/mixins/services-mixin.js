import Vue from 'vue'

import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

export default {
  data () {
    return {
      SERVICES_ENDPOINT: 'http://142.93.173.131/api/services/controller',
      GET_EMPLOYEE_IDS_ENDPOINT: '/employee/id',
      GET_DEPARTMENT_NAMES_ENDPOINT: '/department/name',
      GET_DASHBOARD_CARD_DTO_LIST_ENDPOINT: '/employee/dashboardCardDtoList',
      GET_EMPLOYEE_CARD_DTO_ENDPOINT: employeeId => {
        return '/employee/employeeCardDto?employeeId=' + employeeId
      },
      GET_WEEKLY_SUMMARY_DTO_ENDPOINT: employeeId => {
        return '/workInfo/weekly?employeeId=' + employeeId
      },
      GET_MONTHLY_SUMMARY_DTO_ENDPOINT: employeeId => {
        return '/workInfo/monthly?employeeId=' + employeeId
      },
      GET_VOUCHER_ITEM_DTO_LIST_ENDPOINT: employeeId => {
        return '/voucher/voucherItemDtoList?employeeId=' + employeeId
      },
      GET_AUTHENTICATED_EMPLOYEE_ENDPOINT: authenticationId => {
        return '/authentication/authenticatedEmployee?authenticationId=' + authenticationId
      },
      GET_CONTACT_CARD_DTO_LIST_ENDPOINT: '/employee/contactCardDtoList',
      GET_GAMES_ENDPOINT: '/game',
      SAVE_NEW_AUTHENTICATION_ENDPOINT: '/authentication/save',
      REQUEST_AUTHENTICATION_ENDPOINT: employeeId => {
        return '/authentication/requestAuth?employeeId=' + employeeId
      },
      AUTHENTICATE_EMPLOYEE_ENDPOINT: (employeeId, authKey) => {
        return '/authentication/authenticate?employeeId=' + employeeId + '&authKey=' + authKey
      },
      ADD_VOUCHER_ENDPOINT: (employeeId, ip, type) => {
        return '/voucher/add?employeeId=' + employeeId + '&ip=' + ip + '&type=' + type
      },
      ADD_VOUCHER_WITH_LOCATION_ENDPOINT: (employeeId, type, location) => {
        return '/voucher/addWithLocation?employeeId=' + employeeId + '&type=' + type + '&location=' + location
      },
      ADD_STORY_POINTS_ENDPOINT: (employeeId, storyPointsToAdd) => {
        return '/employee/addStoryPoints?employeeId=' + employeeId + "&storyPointsToAdd=" + storyPointsToAdd
      }
    }
  },
  methods: {
    getEmployeeIds: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_EMPLOYEE_IDS_ENDPOINT
      return this.get(endpoint)
    },
    getDepartmentNames: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_DEPARTMENT_NAMES_ENDPOINT
      return this.get(endpoint)
    },
    getDashboardCardDtoList: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_DASHBOARD_CARD_DTO_LIST_ENDPOINT
      return this.get(endpoint)
    },
    getEmployeeCardDto: function (employeeId) {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_EMPLOYEE_CARD_DTO_ENDPOINT(employeeId)
      return this.get(endpoint)
    },
    getWeeklySummaryDto: function (employeeId) {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_WEEKLY_SUMMARY_DTO_ENDPOINT(employeeId)
      return this.get(endpoint)
    },
    getMonthlySummaryDto: function (employeeId) {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_MONTHLY_SUMMARY_DTO_ENDPOINT(employeeId)
      return this.get(endpoint)
    },
    getVoucherItemDtoList: function (employeeId) {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_VOUCHER_ITEM_DTO_LIST_ENDPOINT(employeeId)
      return this.get(endpoint)
    },
    getAuthenticatedEmployee: function (authenticationId) {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_AUTHENTICATED_EMPLOYEE_ENDPOINT(authenticationId)
      return this.get(endpoint)
    },
    getContactCardDtoList: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_CONTACT_CARD_DTO_LIST_ENDPOINT
      return this.get(endpoint)
    },
    getGames: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.GET_GAMES_ENDPOINT
      return this.get(endpoint)
    },
    saveNewAuthentication: function () {
      let endpoint = this.SERVICES_ENDPOINT + this.SAVE_NEW_AUTHENTICATION_ENDPOINT
      return this.put(endpoint);
    },
    requestAuthentication: function (employeeId) {
      let endpoint = this.SERVICES_ENDPOINT + this.REQUEST_AUTHENTICATION_ENDPOINT(employeeId)
      return this.put(endpoint)
    },
    authenticateEmployee: function (employeeId, authKey) {
      let endpoint = this.SERVICES_ENDPOINT + this.AUTHENTICATE_EMPLOYEE_ENDPOINT(employeeId, authKey)
      return this.get(endpoint)
    },
    addVoucher: function (employeeId, ip, type) {
      let endpoint = this.SERVICES_ENDPOINT + this.ADD_VOUCHER_ENDPOINT(employeeId, ip, type)
      return this.put(endpoint)
    },
    addVoucherWithLocation: function (employeeId, type, location) {
      let endpoint = this.SERVICES_ENDPOINT + this.ADD_VOUCHER_WITH_LOCATION_ENDPOINT(employeeId, type, location)
      return this.put(endpoint)
    },
    addStoryPoints: function (employeeId, storyPointsToAdd) {
      let endpoint = this.SERVICES_ENDPOINT + this.ADD_STORY_POINTS_ENDPOINT(employeeId, storyPointsToAdd)
      return this.post(endpoint)
    },
    get: function (endpoint) {
      return new Promise ((resolve, reject) => {
        Vue.axios.get(endpoint).then(response => {
          resolve(response.data)
        }).catch(error => {
          console.error(error)
          reject(error)
        })
      })
    },
    put: function (endpoint) {
      return new Promise ((resolve, reject) => {
        Vue.axios.put(endpoint).then(response => {
          resolve(response.data)
        }).catch(error => {
          console.error(error)
          reject(error)
        })
      })
    },
    post: function (endpoint) {
      return new Promise ((resolve, reject) => {
        Vue.axios.post(endpoint).then(() => {
          resolve()
        }).catch(error => {
          console.error(error)
          reject(error)
        })
      })
    }
  }
}
