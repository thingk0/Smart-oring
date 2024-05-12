type DashboardProps = {
  resource: {
    read(): any;
  };
};

function Dashboard({ resource }: DashboardProps) {
  const data = resource.read();
  console.log(data);

  return <h1>Dashboard</h1>;
}

export default Dashboard;
