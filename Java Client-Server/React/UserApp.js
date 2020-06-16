import React from 'react';
import UserTable from './User';
import UserForm from './UserForm'
import {GetUsers,DeleteUser,AddUser} from './rest'
import './UserApp.css'

class UserApp extends React.Component{

	constructor(props){
		super(props);
		this.state={
			users:[{"username":"maria","password":"maria"},{"username":"elena","password":"maria"},{"username":"margareta","password":"maria"},],
			selectedUsers:new Set(),
		}
		console.log('UserApp constructor')
	}

	componentDidMount(){
		console.log('component loaded');
		document.addEventListener("keydown", this.deleteFunction, false);
		GetUsers().then(users=>this.setState({users}))
	}

	componentWillUnmount(){
		document.removeEventListener("keydown",this.deleteFunction,false);
	}

	deleteFunction=e=>{
		if(e.keyCode===46){
			for(const user of this.state.selectedUsers){
				   DeleteUser(user)
		            .then(res=> GetUsers())
		            .then(users=>this.setState({users}))
		            .catch(error=>console.log('eroare delete', error));
			}
		}
	}

	addFunction=user=>{
		console.log('inside add func',user);
		AddUser(user)
		.then(res=>GetUsers())
		.then(users=>this.setState({users:users}))
		.catch(error=>console.log('eroare add',error));
	}

	toggleCheckbox = username => {
	    if (this.state.selectedUsers.has(username)) {
	      this.state.selectedUsers.delete(username);
	    } else {
	      this.state.selectedUsers.add(username);
	    }
	}

	render(){
		return(
			<div className="UserApp">
			<h1>Competition User Management</h1>
			<UserTable users={this.state.users} toggleCheckboxFunction={this.toggleCheckbox} />
			<br/>
			<UserForm addFunction={this.addFunction}/>
			</div>
		);
	}
}
export default UserApp;