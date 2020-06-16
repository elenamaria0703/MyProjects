import React from 'react';
import CheckBox from './Checkbox'
 class UserRow extends React.Component{
 	render(){
 		return(
 			<tr>
	 			<td>{this.props.user.username}</td>
	 			<td>{this.props.user.password}</td>
	 			<td><CheckBox key={this.props.user.username} username={this.props.user.username} handleCheckboxChange={this.props.toggleCheckboxFunction}/></td>
 			</tr>
 		);
 	}
 }

 class UserTable extends React.Component{
 	render(){
 		var rows=[];
 		var myToggleFunction=this.props.toggleCheckboxFunction
 		this.props.users.forEach(function(user){
 			rows.push(<UserRow user={user} key={user.username} toggleCheckboxFunction={myToggleFunction}/>);
 		});
 		return(<div className="UserTable">
 			<table className="center">
	 			<thead>
	 			<tr>
		 			<th>Username</th>
		 			<th colSpan="2">Password</th>
		 		</tr>
		 		</thead>
		 		<tbody>{rows}</tbody>
		 	</table>
		 	</div>
		);
 	}
 }

 export default UserTable;