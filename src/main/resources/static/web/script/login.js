const {createApp} = Vue;

createApp({
  data(){
    return{
        email: "",
        password: "",
      }
    },

    methods: {
        login() {
            console.log(this.email, this.password)
            axios.post('/api/login',`email=${this.email}&password=${this.password}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })

            .then(response => {
              Swal.fire({
                icon: 'success',
                title: 'Signed in successfully',
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                }
              })
              setTimeout(() =>{if(this.email.includes("admin") ){
                window.location.href = "../loanManager.html"
                }else{
                window.location.href ="/web/accounts.html"
                }} , 2000);
            }).catch(error => {
                Swal.fire({
                  title: "THE INFORMATION IT´S NOT CORRET",
                  icon: 'warning',
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown'
                  }
                })
            })
        },
    
  }

}).mount("#app")
