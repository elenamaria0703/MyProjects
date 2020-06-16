import React from 'react';

class UserForm extends React.Component{
	constructor(props){
		super(props);
		this.state={
			id:'',
			username:'',
			password:''
		};
	}
	handleUsernameChange=event=>{
		this.setState({username:event.target.value});
		this.setState({id:event.target.value});
	}
	handlePasswordChange=event=>{
		this.setState({password:event.target.value});
	}
	handleSubmit=event=>{
		var user={
			id:this.state.id,
			username:this.state.username,
			password:this.state.password
		}
		console.log('A user was submitted');
		console.log(user);
		this.props.addFunction(user);
		this.setState({id:'',username:'',password:''});
		event.preventDefault();
	}
	render(){
		return (
			<form onSubmit={this.handleSubmit}>
				<label>
					Username:
					<input type="text" value={this.state.username} onChange={this.handleUsernameChange}/>
				</label><br/><br/>
				<label>
					Password:
					<input type="password" value={this.state.password} onChange={this.handlePasswordChange}/>
				</label><br/><br/>
				<input type="submit" value="Submit"/>
			</form>
		);
	}
}

export default UserForm;