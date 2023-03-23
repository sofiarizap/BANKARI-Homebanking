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
      name:"",
      maxAmount:"",
      payments:[],
      interest:"",

    }
  },
  created(){
    /* this.loadData(); */
  },  
  methods:{
    loadData: function(){
      this.url = "http://localhost:8080/api/clients"
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

    }, crearPrestamo() {
      console.log(this.payments)
      axios.post("/api/admin/loan",`name=${this.name}&maxAmount=${this.maxAmount}&payments=${this.payments}&interest=${this.interest}`)
          .then(res => {
              Swal.fire({
                icon: 'success',
                title:"NEW LOAN CREATED",
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
              })
              setTimeout(() => window.location.reload())
          })
          .catch(error => {
            Swal.fire({
              icon: 'warning',
              title: error.response.data,
              showClass: {
                popup: 'animate__animated animate__fadeInDown'
              },
            })
          });

  },
  logout() {
    axios.post('/api/logout')
    .then(window.location.href = "../web/index.html")

}
  } 
}).mount("#app")


