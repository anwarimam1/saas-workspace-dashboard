import React from 'react'
import UserForm from '../components/UserForm'
import UserList from '../components/UserList'

const Users = () => {
  return (
    <div className="space-y-6">
        <UserForm />
        <UserList />
    </div>
  )
}

export default Users