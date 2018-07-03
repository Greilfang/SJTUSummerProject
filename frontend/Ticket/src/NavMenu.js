import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Home from '@material-ui/icons/Home';
import ShoppingCart from '@material-ui/icons/ShoppingCart';
import Person from '@material-ui/icons/Person';
import Collections from '@material-ui/icons/Collections';
import Divider from '@material-ui/core/Divider';
import Bookmark from '@material-ui/icons/Bookmark';
import LogoutVariant from 'mdi-material-ui/LogoutVariant';
import FormDialog from "./FormDialog";

// not login
export const NavMenuList1 = (
    <div>
        <ListItem>
            <ListItemIcon><AccountCircle/></ListItemIcon>
            <FormDialog/>
            <ListItemText inset primary=''/>
        </ListItem>
        <ListItem button onClick={() => {alert("Login")}}>
            <ListItemIcon><AccountCircle/></ListItemIcon>
            <ListItemText inset primary='Sign in'/>
        </ListItem>
        <ListItem button onClick={() => {alert("Market")}}>
            <ListItemIcon><Home color='primary'/></ListItemIcon>
            <ListItemText inset primary='Home'/>
        </ListItem>
        <ListItem button onClick={() => {alert("Concert")}}>
            <ListItemIcon><Bookmark color='secondary'/></ListItemIcon>
            <ListItemText inset primary='Concert'/>
        </ListItem>
        <ListItem button onClick={() => {alert("Show")}}>
            <ListItemIcon><Bookmark color="error"/></ListItemIcon>
            <ListItemText inset primary='Show'/>
        </ListItem>
    </div>
);

// login
export const NavMenuList2 = (
    <div>
        <ListItem button>
            <ListItemIcon><Person/></ListItemIcon>
            <ListItemText inset primary='Account'/>
        </ListItem>
        <ListItem button>
            <ListItemIcon><ShoppingCart/></ListItemIcon>
            <ListItemText inset primary='Cart'/>
        </ListItem>
        <ListItem button>
            <ListItemIcon><Collections/></ListItemIcon>
            <ListItemText inset primary='Collection'/>
        </ListItem>
        <Divider/>
        <ListItem button onClick={() => {alert("Market")}}>
            <ListItemIcon><Home/></ListItemIcon>
            <ListItemText inset primary='Home'/>
        </ListItem>
        <ListItem button onClick={() => {alert("Concert")}}>
            <ListItemIcon><Bookmark/></ListItemIcon>
            <ListItemText inset primary='Concert'/>
        </ListItem>
        <ListItem button onClick={() => {alert("Show")}}>
            <ListItemIcon><Bookmark/></ListItemIcon>
            <ListItemText inset primary='Show'/>
        </ListItem>
        <Divider/>
        <ListItem button>
            <ListItemIcon><LogoutVariant/></ListItemIcon>
            <ListItemText inset primary='Logout'/>
        </ListItem>
    </div>
);

