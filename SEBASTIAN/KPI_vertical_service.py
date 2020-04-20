'''
How does it work ?

1. Make sure that in your NFVO (either OSM or TIMEO or whatever), there are no duplicate network services instantiated or something that cannot allow to create and instantiate network slices."
2. Delete the VSMF_APP log. In my case are located into /var/log/sebastian/vsmf.log.
3. Either set the log path of VSMF at /var/log/sebastian/vsmf.log or modify the hardcoded one according to yours.
4. Delete the NSMF_APP log. In my case are located into /var/log/sebastian/nsmf.log.
5. Either set the log path of NSMF at /var/log/sebastian/nsmf.log or modify the hardcoded one according to yours.
6. Instanciate and then terminate a vertical service instance.
7. Execute this python script: it parses the log files of VSMF and NSMF and computes the time to instanciate and terminate a vertical service instance.

'''
def get_KPI_from_log(filename, entity):
	KPI_list = []
	lines = open(filename, "r")
	for line in lines:
		if("KPI" in line):
			element = line.split("KPI")[1]
			ts, desc_event=element.split(",")
			ts = ts[1:]
			desc_event = desc_event[:-1]
			event = {}
			event["ts"]=ts
			event["entity"]=entity
			event["desc_event"]=desc_event[1:]
			KPI_list.append(event)
	return KPI_list


def sort_array(array):
	for i in range(0,len(array)):
		for j in range(i+1,len(array)):
			if(array[i]["ts"]>array[j]["ts"]):
				tmp=array[i]
				array[i]=array[j]
				array[j]=tmp
	return array


def get_events_list():
	events_list = []
	KPI_dsp=get_KPI_from_log("/var/log/sebastian/vsmf.log","DSP")
	KPI_nsp=get_KPI_from_log("/var/log/sebastian/nsmf.log","NSP")
	events_list.extend(KPI_dsp)
	events_list.extend(KPI_nsp)

	events_list=sort_array(events_list)
	return events_list



events_list = get_events_list()
starting_ts = events_list[0]["ts"]
instantiation_time = -1
termination_time = -1

for event in events_list:
	#print(event)
	if(event["desc_event"]=="Received request to terminate VS instance."):
		instantiation_time = delta_ts
		starting_ts=event["ts"]
		print("\n")

	delta_ts= int(event["ts"])-int(starting_ts)
	print(event["entity"]+", "+str(delta_ts)+", "+event["desc_event"])
	termination_time = delta_ts

instantiation_time = round(instantiation_time/1000,2)
termination_time = round(termination_time/1000,2)

print("\nTotal instantiation time: "+str(instantiation_time)+" seconds.")
print("Total termination time: "+str(termination_time)+" seconds.\n")
