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
                position: 'top-end',
                icon: 'success',
                title: 'Signed in successfully',
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
                hideClass: {
                  popup: 'animate__animated animate__fadeOutUp'
                }
              })
              setTimeout(() => window.location.href = "/web/accounts.html", 4000);
              console.log('signed in!!!');

            }).catch(error => {
              console.log(error.response)
                "error de email o password"
                Swal.fire({
                  position: 'top-end',
                  title: "THE INFORMATION IT´S NOT CORRET",
                  icon: 'warning',
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown'
                  },
                  hideClass: {
                    popup: 'animate__animated animate__fadeOutUp'
                  }
                })

            })
        },
    logout() {
        axios.post('/api/logout')
        .then(window.location.href = "/web/index.html")

    }
  }

}).mount("#app")
