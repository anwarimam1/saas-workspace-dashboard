import React from 'react'
import WorkspaceForm from '../components/WorkspaceForm'
import WorkspaceList from '../components/WorkspaceList'

const Workspaces = () => {
  return (
    <div className="space-y-6">
        <WorkspaceForm />
        <WorkspaceList />
    </div>
  )
}

export default Workspaces