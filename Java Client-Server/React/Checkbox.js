import React from 'react';
class CheckBox extends React.Component{
 	constructor(props){
 		super(props);
 		this.state={
 			isChecked:false,
 		}
 	}
 	toggleCheckboxChange=()=>{
 		this.setState({isChecked:!this.state.isChecked});
 		this.props.handleCheckboxChange(this.props.username);
 	}
 	render(){
 		return <input type="checkbox" name={this.props.username} checked={this.state.isChecked} onChange={this.toggleCheckboxChange}/>
 	}
 }

 export default CheckBox;