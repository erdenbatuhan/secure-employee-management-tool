import {firebaseDb} from "../main.js";

export default {
  data () {
    return {
      name: 'pollService'
    }
  },
  methods: {
    getPollsPromise: function () {
      return new Promise((resolve,reject) => {
        firebaseDb.collection('polls').get().then((querySnapshot => {
          resolve(querySnapshot);
        })).catch(error => {
          reject(error)
        });
      })
    },
    getPollWithPollNamePromise: function (pollName) { // IMPORTANT: pollName is jellyPoll or portalPoll
      return new Promise((resolve, reject) => {
          firebaseDb.collection('polls').get()
                    .collection(pollName).get().then((querySnapshot => {
            resolve(querySnapshot);
          })).catch(error => {
            reject(error)
          })
      })
  },
    getPollItemsWithPollNamePromise: function (pollName) {
      return new Promise((resolve, reject) => {
        let path = '/' + pollName + 'Items';
        let pollItems = [];

        firebaseDb.collection(path).get().then((querySnapshot => {
          querySnapshot.forEach(function (doc) {
            pollItems.push(doc.data());
          });

          resolve(pollItems);
        })).catch(error => {
          reject(error)
        });
      })
    },
    addPollWithName: function (pollName) {
      firebaseDb.collection('polls').collection(pollName).set({
        pollItems: []
      });
    },
    addPollItemTo: function (pollName, image) {  // image is like background.png, only the name not the path.
      let path = '/' + pollName + 'Items';
      let numberOfItems = this.getPollWithPollName(pollName).length;
      let imagePath = '../../static/images/' +  pollName + image;  // IMPORTANT: directory names should be camelCase

      firebaseDb.collection(path).doc(numberOfItems).set({
        ratings: [{}],
        imagePath: imagePath
      })
    },
    addRatingTo: function (pollName, pollItem, rating) {  // jelly/pollItems/1 is a pollItem, pollName is jellyPoll
      let itemRatings = this.getPollWithPollNamePromise(pollName).collection('ratings');
      let numberOfRatings = itemRatings.length;

      itemRatings.doc('rating' + numberOfRatings).set({
        rating: {
          employeeComment: rating.employeeComment,
          employeeId: rating.employeeId,
          itemRatings: {
            mainRating: rating.ratings.mainRating,
            npcRating: rating.ratings.npcRating,
            skinRating: rating.ratings.skinRating,
            villainRating: rating.ratings.villainRating
          }
        }
      })
    }
  }
}
