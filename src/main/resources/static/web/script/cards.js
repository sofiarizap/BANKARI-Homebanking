const{createApp} = Vue; 

createApp({
  data(){
    return{
      cards:{},
      client:{},
      creditCard:[],
      debitCard:[],
      checkType: [],
      checkColor: [],
      cardsActive: [],
      cardExpired:"",
      
    }
  },
  created(){
    this.loadData()
    this.cardVencida()
    
  },
  methods:{
    loadData: function(){
      axios.get("/api/clients/current")
      .then( res =>{
        this.client= res.data;
        this.cards = res.data.card
        this.cardsActive = this.cards.filter(card => card.active == true)
        this.creditCard=  this.cardsActive.filter(card=> card.type =="CREDIT")
        this.debitCard =  this.cardsActive.filter(card=> card.type =="DEBIT")
      })
      .catch((error)=>{console.log(error)})

  },
  newCard() {
    axios.post("/api/clients/current/cards ", "cardType=" + this.checkType + "&cardColor=" + this.checkColor, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
          Swal.fire({
            icon: 'success',
            title: 'PAYMENT SUCCESSFUL',
            showClass: {
              popup: 'animate__animated animate__fadeInDown'
            },
          })
          setTimeout(() => window.location.href = "/web/cards.html", 2000)
        })
        .catch(error => {
          "error"
          Swal.fire({
            icon:'warning',
            title: error.response.data,
            showClass: {
              popup: 'animate__animated animate__fadeInDown'
            },
          })
      });
},
cardVencida(){
    axios.get("/api/clients/current")
    .then( res =>{
      this.cards = res.data.card
      var today = new Date();
      var day = today.getDate();
      var month = today.getMonth() + 1;
      var year = today.getFullYear();
      this.cardExpired=this.cards.filter(card => card.truDate < `${year}-${month}-${day}`);
      this.cardExpired=this.cardExpired.find(number=>number.number)
    })
    .catch((error)=>{console.log(error)})
  
},
deleteCard(id) {
  axios.patch(`/api/clients/current/cards/delete/` + id)
      .then(response => {
          window.location.reload()
      })
},

}
}).mount("#app")
