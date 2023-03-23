const {createApp} = Vue;

createApp({
  data(){
    return{
      url:"",
      client:{},
      accounts:{},
      loans:{},
    }
  },
  created(){
    this.url = "http://localhost:8080/api/clients/current";
    this.loadData() 
    console.log(this.accounts)
  },  
  methods:{
    loadData: function(){
      axios.get(this.url)
      .then( res =>{
        this.client= res.data;
        this.accounts= res.data.accounts.sort((a,b)=>a.id-b.id)
        this.loans=res.data.loans
        
      })
      .catch((error)=>{console.log(error)})
    },
    
    logout() {
      axios.post('/api/logout')
      .then(res =>{
        setTimeout(() => window.location.href = "/web/login.html", 4000)
      })
        
    },
    createAccount: function(){
      axios.post('/api/clients/current/accounts') 
        .then(res => {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Account Created',
            showClass: {
              popup: 'animate__animated animate__fadeInDown'
            },
            hideClass: {
              popup: 'animate__animated animate__fadeOutUp'
            }
          })
          setTimeout(() =>  window.location.reload(), 4000)
      })
      
    },
    deleteAccount(id) {
      axios.patch(`/api/clients/current/accounts/delete/` + id)
          .then(response => {
              window.location.reload()
          })
  },
    
  } 
}).mount("#app")


