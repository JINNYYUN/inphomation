//로그인을 했을때만 실행함


/*글쓰기로 이동 인자값 노필요	*/
	function goWrite(){
		location.href= 'http://'+location.host+"/Inphomation/post"
	}
	
/* dropBox의 활성화 비활성화를 위한 function */
	var dropBtn = document.getElementById('drop-btn')
	var dropDownMenu = document.getElementById('drop-menu')
	dropBtn.addEventListener('click',function(event){
		if(this.active){
			dropDownMenu.classList.remove('active')
		}else{
			dropDownMenu.classList.add('active')
			}
		this.active = !this.active
	})
	
	function doLogout(){
		$.ajax({
			url:"logout",
			type:"get",
			success:function(){
				alert('Do Logout')
				window.location.reload()
			},error:function(){
				alert('err')
			}
		})
	
	}