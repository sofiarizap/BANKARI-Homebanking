const {createApp} = Vue;

createApp({
  data(){
    return{
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      }
    },

    methods: {
      addClient(){
        if(this.firstName && this.lastName && this.email.includes("@") && this.password){
            this.register();
        }
      },
      register()  {
      axios.post('/api/clients', "firstName=" + this.firstName +  "&lastName=" +  this.lastName + "&email=" + this.email + "&password=" +this.password, {headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(res => {
              this.login();
        })
      },
      login()  {
        axios.post("/api/login","email="+this.email+"&password="+this.password, {headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(res => {
          Swal.fire({
            icon: 'success',
            title: 'WELCOME',
            showClass: {
              popup: 'animate__animated animate__fadeInDown'
            },
          })
          setTimeout(() => window.location.href = "/web/accounts.html", 2000)
      })
            .catch(error => {
                console.error(error);
            })
    },
    
  }

}).mount("#app")
