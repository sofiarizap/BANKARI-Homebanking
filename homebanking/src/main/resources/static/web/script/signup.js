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
      AddClient() {
          if (this.firstName && this.lastName && this.email.includes("@")) {
              let client = {
                  firstName: this.firstName,
                  lastName: this.lastName,
                  email: this.email,
                  password: this.password,
              }
              this.postClient(client)
          }
      },

      postClient(client) {
          axios.post('/api/clients', "firstName=" + client.firstName + "&lastName=" + client.lastName + "&email=" + client.email + "&password=" + client.password, 
          { headers: { 'content-type': 'application/x-www-form-urlencoded' } })

              .then(response => {
                  axios.post('/api/login',`email=${this.email}&password=${this.password}`, {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                      }
                  })
              })
              .then(response => {
              window.alert("SUCCESSFUL REGISTRATION!")
              
              
              axios.post('/api/clients/current/accounts') 
                .then( setTimeout(() => window.location.href = "/web/accounts.html", 3000))
              
              }
              )
              
          .catch(error => {
            Swal.fire({
              position: 'top-end',
              title: error.response.data,
              icon: 'warning',
              showClass: {
                popup: 'animate__animated animate__fadeInDown'
              },
              hideClass: {
                popup: 'animate__animated animate__fadeOutUp'
              }
            })
          });
      }

  }

}).mount("#app")