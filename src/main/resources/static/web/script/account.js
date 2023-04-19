const{createApp} = Vue; 

createApp({
  data(){
    return{
      url:"",
      id: "",
      urlParameter:"",
      parameter : undefined,
      transactions:"",
      account:"",
      balance:"",
    }
  },
  created(){
    this.urlParameter = location.search
    this.parameter = new URLSearchParams(this.urlParameter)
    this.id = this.parameter.get("id")
    this.loadData()
  },
  methods:{
    loadData: function(){
      this.url =`/api/accounts/${this.id}`
      axios.get(this.url)
      .then( res =>{
        this.transactions = res.data.transactions.sort((a, b)  => b.id - a.id)
        this.client= res.data;
        this.account= res.data.number
        this.balance= res.data.balance
      })
      .catch((error)=>{console.log(error)})
  },

}
}).mount("#app")
