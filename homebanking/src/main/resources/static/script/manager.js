const {createApp} = Vue;

createApp({
  data(){
    return{
      url:"",
      clients:[],
      listClients:{},
      first:"",
      last:"",
      email:"",
    }
  },
  created(){
    this.url = "http://localhost:8080/clients";
    this.loadData();
    console.log(this.listClients)
  },  
  methods:{
    loadData: function(){
      this.url = "http://localhost:8080/clients"
      axios.get(this.url)
      .then( res =>{
        this.listClients= res;
        console.log(this.listClients)
        this.clients=this.listClients.data._embedded.clients.map(client=>({...client}));
      })
      .catch((error)=>{console.log(error)})
    },
    addClient: function(){
      if(this.first && this.last && this.email){
        this.postClient();
      }
    },
    postClient: function(){
      axios.post(this.url,{
        firstName:this.first,
        lastName:this.last,
        email:this.email
      })
        .then(res=>{
          this.first = "",
          this.last = "",
          this.email= ""
          this.loadData()
        }).catch((error)=>{console.log(error)});
    },
    deleteClient: function(client){
      axios.delete(client)
    }
  } 
}).mount("#app")


