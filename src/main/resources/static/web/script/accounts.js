const {createApp} = Vue;

createApp({
  data(){
    return{
      url:"",
      client:{},
      accounts:{},
      loans:{},
      accountsActive: [],
      accountType:"",
      interesTotal:"",
      
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
        this.accountsActive= this.accounts.filter(account => account.active == true)
        console.log(this.accountsActive)
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
      axios.post("/api/clients/current/accounts", "accountType=" + this.accountType, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(res => {
          Swal.fire({
            icon: 'success',
            title: 'Account Created',
            showClass: {
              popup: 'animate__animated animate__fadeInDown'
            },
          })
          setTimeout(() =>  window.location.reload(), 2000)
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


