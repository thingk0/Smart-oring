import ReactApexChart from 'react-apexcharts';

type HeatmapProps = {
  resource: {
    read(): any;
  };
};

function Heatmap({ resource }: HeatmapProps) {
  const data = resource.read();
  //   console.log(data);

  const option = {
    colors: ['#000000'],
    title: { text: 'bottleneck charts' },
    dataLabels: { enabled: true },
  };

  return (
    <>
      <ReactApexChart type="heatmap" series={data} options={option} />
    </>
  );
}

export default Heatmap;
