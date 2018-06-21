<script>

	function getBoforeDate(before){
		var now = new Date();
		now.setDate(now.getDate()-before);
		return now;
	}
	
	function getBoforeMonth(beforeMonth,day){
		var now = new Date();
		now.setDate(day);
		now.setMonth(now.getMonth()-beforeMonth);
		return now;
	}
	
	$(function(){
		
		var now = new Date();
		
		$('.date').daterangepicker({
		    "showWeekNumbers": true,
		    "showISOWeekNumbers": true,
		    "ranges": {
		        "Today": [
		            now,
		            now
		        ],
		        "Yesterday": [
					getBoforeDate(1),
					getBoforeDate(1)
		        ],
		        "Last 7 days": [
					getBoforeDate(7),
					now
		        ],
		        "Last 30 days": [
		            getBoforeDate(30),
		            now
		        ],
		        "Current month": [
		            getBoforeMonth(0,1),
		            getBoforeMonth(0,31)
		        ],
		        "Last month": [
					getBoforeMonth(1,1),
					getBoforeMonth(1,31)
		        ],
		        "Last 3 monthes": [
						getBoforeMonth(2,1),
						getBoforeMonth(0,31)
			        ]
		    },
		    "locale": {
		        "format": "YYYY/MM/DD",
		        "separator": "-",
		        "applyLabel": "Apply",
		        "cancelLabel": "Cancel",
		        "fromLabel": "From",
		        "toLabel": "To",
		        "customRangeLabel": "Customize",
		        "weekLabel": "W",
		        "daysOfWeek": [
		            "Sunday",
		            "Monday",
		            "Tuesday",
		            "Wednesday",
		            "Thursday",
		            "Friday",
		            "Saturday"
		        ],
		        "monthNames": [
		            "January",
		            "February",
		            "March",
		            "April",
		            "May",
		            "June",
		            "July",
		            "August",
		            "September",
		            "October",
		            "November",
		            "December"
		        ],
		        "firstDay": 1
		    },
		    "alwaysShowCalendars": true,
		    "autoUpdateInput":false,
		    "opens": "right",
		    "buttonClasses": "btn btn-sm"
		}, function(start, end, label) {
		  console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
		});
		
		$('.date').on('apply.daterangepicker', function(ev, picker) {
            $(this).val(picker.startDate.format('YYYY/MM/DD') + ' - ' + picker.endDate.format('YYYY/MM/DD'));
        });

        $('.date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
        });
		
	});
</script>