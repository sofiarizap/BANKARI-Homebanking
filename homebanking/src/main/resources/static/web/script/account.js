const{createApp} = Vue; 

createApp({
  data(){
    return{
      url:"",
      id: "",
      urlParameter:"",
      parameter : undefined,
      transactions:"",
    }
  },
  created(){
    this.urlParameter = location.search
    this.parameter = new URLSearchParams(this.urlParameter)
    this.id = this.parameter.get("id")
    this.loadData()
    console.log(this.id)
  },
  methods:{
    loadData: function(){
      this.url =`http://localhost:8080/api/accounts/${this.id}`
      axios.get(this.url)
      .then( res =>{
        this.transactions = res.data.transactions.sort((a, b)  => b.id - a.id)
      })
      .catch((error)=>{console.log(error)})

  }

}
}).mount("#app")
