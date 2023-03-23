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
      
    }
  },
  created(){
    this.loadData()
    
  },
  methods:{
    loadData: function(){
      axios.get("http://localhost:8080/api/clients/current")
      .then( res =>{
        this.client= res.data;
        this.cards = res.data.card
        this.creditCard= res.data.card.filter(card=> card.type =="CREDIT")
        this.debitCard = res.data.card.filter(card=> card.type =="DEBIT")
      })
      .catch((error)=>{console.log(error)})

  },
  newCard() {
    axios.post("/api/clients/current/cards ", "cardType=" + this.checkType + "&cardColor=" + this.checkColor, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
            window.alert("THE CARD HAS BEEN CREATED SUCCESSFULLY")
            window.location.href = "/web/cards.html"
        })
        .catch(error => {
            "error"
            window.alert(error.response.data)
        });
},

}
}).mount("#app")
