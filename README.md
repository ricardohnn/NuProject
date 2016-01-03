# NuProject

I will update the README with some thoughts about implementation which i could have used but i didn't (for n-reasons) use and
also some links.

1 - The Http/json request consumer

How to test a request before really implementing it?

https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en

I really enjoy this plugin, it makes the "first glance" of the problems a little bit easier (at least for me).

Ok, I won't talk about http and asyncs, but i will discard robospice since volley can basically do the same.
But on the meantime, some new names came up "okHttp" and "retrofit". Retrofit seems to be faster than volley (which i rather
prefer and the one i will use, since it is maintained by Google - high five to that):

http://stackoverflow.com/questions/16902716/comparison-of-android-networking-libraries-okhttp-retrofit-volley

Also, retrofit supports okHttp (which i still haven't a reason to use) but seems like jake Wharton made possible to use volley
with okHttp by extending HurlStack (any further explanations about volley can be found in here i think- haven't read it all):

http://www.programering.com/a/MTO3UzMwATk.html
https://plus.google.com/+JakeWharton/posts/eJJxhkTQ4yU
https://gist.github.com/JakeWharton/5616899

Some nice comparison is also in here:

https://medium.com/android-news/android-networking-i-okhttp-volley-and-gson-72004efff196#.hyuz1ufrm
http://instructure.github.io/blog/2013/12/09/volley-vs-retrofit/

So i went with volley and gson (i will make comments about going with jackson because, as far as i remember, jackson was a little
bit harder to work with, and honestly, right now, i don't have time).

Just some old thought that came to my mind right now, when i was doing an application for 99taxi/easytaxi job interview i have seen
something about flatbuffers, which i added the following remark:

http://frogermcs.github.io/json-parsing-with-flatbuffers-in-android/
"sounds interesting but won't be checking it right now, but good for performance improvement
(maybe changing from json to flatbuffers would be good in server side also)".

2 - Layouts

I am changing the view pager created by Android studio default since it has a lot of deprecated actionbar stuff, but i will be using
Google's swipe view example:

http://developer.android.com/training/implementing-navigation/lateral.html 

Seems to fit perfectly for my needs right now.
Ok, so Nu requested a HEADER in the layout, which i really don't understand what they would consider a header, i think it just means
that it won't be scrollable together with the listview (which IMHO, it basically takes around 40% of the screen with lots of spacing
- which makes it pretty - but, if a user would scroll to see the list, then i think the list should take the focus in here, so i would
think about resizing the total payment box - but never, never hide it - and hide the "gerar boleto" together with the month's total
- which seems to be a little duplicated(?) from the box information).

Nu uses a horizontal scroll view for the dates inside a framelayout (with a back button, which I won't be using since... i have no
parent =T to go back to), but the dumpview does not show anything regarding the changing color's arrow, so i might guess it has some
image being set the same colors as the text.

Using apktool, it seems that i have found about it in activity_bill_detail.xml:
 <com.nu.custom_ui.pager_triangle.PagerSlidingTabStrip android:layout_gravity="center" android:id="@id/tabs" android:paddingLeft="0.0dip" android:paddingTop="12.0dip" android:paddingRight="0.0dip" android:paddingBottom="4.0dip" android:layout_width="fill_parent" android:layout_height="wrap_content" app:pstsPaddingMiddle="true" />

Well, we will keep it simple (which means i will focus on treating the logics first).

3 - The parsing

Now that's a huge parsing, I am kinda glad to use volley right now, since getting all these in the first screen would be the best to do
(I think...).

This seems really an interesting link:

http://www.javacreed.com/gson-deserialiser-example/

Maybe i will read it if i have a free time.
I am able to retrieve the json, but href gives me a null pointer. Although it is correct, something seems fishy.

Geez, i just realized that not all the fields are required, so rather than returning null, they do not keep the json stable, therefore the
fields can/will change (facepalm).
I need to make a check on which fields are required, right now, i am able to parse everything and show it in a scrollview, so...
getting/parsing the json is finished, just missing some details.

4 - The db

Ok, so today i was checking how to send the information forward. I've thought that making it parcelable would be a good choice, but...
everytime keeping a array of bill objects, seems a little... stupid? So i will be saving them inside a db, which would be the correct
solution to begin with. I will use the same beans (or at least reuse) that i generated for the json parsing with gson, but instead of
making everything serializable, i will make some id fields not serializable (aka transient).

http://www.javacreed.com/gson-annotations-example
http://camelcode.org/tutorial/Exclude-fields-from-JSON-with-java-modifiers-using-Gson.htm
http://camelcode.org/tutorial/Exclude-fields-from-JSON-using-Gson-with-@Expose.htm#82

Merry xmas! finally the db is partially done! I started by creating the DB as usual, but a friend of mine said that he used a nice lib to
make things easier. Sugar ORM!
And what a piece of s* that was... Come on devs, make libs and make documentation... it was painful to make it almost work (almost a day)
and then, i realized that i needed to check if sugar ORM was the best choice, since relations seemed really... lacking...
I've made a small research and found out that GreenDAO and DBFlow seemed to be the best choices for SQL:

http://www.raizlabs.com/dev/2015/02/go-dbflow-fastest-android-orm-database-library/

I know that the research was done by the devs of dbflow but they seemed to convince me to use the dbflow.
And... more painful stuff to do before everything got settled. Android Studio seems to has some issues with gradle build, and was showing
an strange error. I needed to go to File>invalidate caches/restart, in order to fix the errors that was showing, update everything and then,
just then, see the real error.
The id of a class, which was used as a primary key and exported as a foreign key to other classes, had the private modifier. But hey... i've
done my getters and setters... but, that was not enough! You must remove the private modifier in order to make it work.

I wasted almost... 2 days with DBFlow, 1 with sugar orm and half a day with usual db implementation.
I hope to have everything working until monday... oh... and the unit tests...

I am now saving to the db correctly... well... almost.
At first glance, i have not seen that there are no ID for some of the bill input. I mean... why?! Just why?
I will now analyze what fields are missing (let's just considerer that any field inside bill can be empty), but for that, i have to search
for a better sql viewer, just to make things easier.
SqliteSpy seems pretty much ok for now, but is so annoying to keep dumping the db everytime.
Since i don't have much time, i will use genymotion (nexus 5 configs) and just a simple script to dump and send as a parameter to sqlitespy:

import os

os.system("rm ./nuProjectDatabase.db")
os.system("adb wait-for-device; adb pull /data/data/com.rdzero.nuproject/databases/nuProjectDatabase.db ./nuProjectDatabase.db")
os.system("/Downloads/SQLiteSpy_1.9.10/SQLiteSpy.exe ./nuProjectDatabase.db &")
os.system("adb shell pm clear com.rdzero.nuproject")

That will be enough for now. I will need to re-run the app everytime to save the db, but it is easier to check any modification to the db.

Ok... so bill has only 1 field that seems to be required, the state one.
So i cannot use any of these fields as a primary key, which means, i would probably use an autoincrement id as primary key (which will be my
foreign key for the other tables).

That worked, but that will also affect any deletion/insertion since i cannot go directly to a defined id.

SELECT NuBillContract.id, NuBillContract.billId, NuBillContract.state, NuBillContract.barcode, NuBillContract.linhaDigitavel,
NuSummaryContract.dueDate, NuSummaryContract.closeDate, NuSummaryContract.pastBalance, NuSummaryContract.totalBalance, NuSummaryContract.interest, NuSummaryContract.totalCumulative, NuSummaryContract.paid, NuSummaryContract.minimumPayment, NuSummaryContract.openDate,
NuLinksContract.self, NuLinksContract.boletoEmail, NuLinksContract.barcode,
NuLineItemsContract.postDate, NuLineItemsContract.amount, NuLineItemsContract.title, NuLineItemsContract."index", NuLineItemsContract.charges, NuLineItemsContract.href 
FROM NuBillContract, NuSummaryContract, NuLinksContract, NuLineItemsContract
WHERE NuBillContract.id=4
AND NuSummaryContract.nuBillContract_id=NuBillContract.id
AND NuLineItemsContract.nuBillForeignKeyContainer_id=NuBillContract.id
AND NuLinksContract.nuBillContract_id=NuBillContract.id

I will be running the query above just to check some important fields that might be missing.
So checking the worst case scenario (that is used on this project, which means, not necessarily the real worst case) I have that the following
fields are not required:

Bill Contract
id, barcode, linhaDigitavel

Summary Contract
paid

Links Contract (consider href)
self, boletoEmail, barcode

Line Items Contract (check that amount can be a negative number, which is the one that makes the rest of the values empty)
index, charges, href

I don't have a problem with all these fields, since it returns null from the json parsing, the only problem is the links which can be an empty
object, but in order to have a better db (without holes) i will create some default values: if it is null then add an empty string or a 0 value.
Since i am focusing on making the "back-end" stable, i will be making the network validation also.

Docs in DBFlow are so f* bad that you cannot even copy and test, it seems like every example in there was just to say "hey we have this, but
you need to find out how to use it, cause f* you".

Ok, made all the basic sql request, it is writting in the DB and loading correctly, not actually worrying about update (because i don`t have time),
so for now, only add and search will be implemented.

I'm a little disapointed since i won't be able to finish it on time, but as the "chief" said, this will be good to learn something new and at the
same time, challenging. Well, i'll give him that... I needed to study things that i have forgotten long ago, and some other new things and new libs.
Although i won't be able to deliver on time, i will still finish this (as always, i am only able to work on this project on weekends) in order to
improve my knowledge and perhaps share some.

Anyway, since i have no knowledge on unit testing, and most of the places i have worked had no concern on that either, i will have to study it from
zero.

Well let's start with what is a mocking framework:

http://codetunnel.io/what-is-a-mocking-framework-why-is-it-useful/

Although i was not able to separate the project as informed by this tutorial (not sure why Android Studio complicates so much the creation of new
folders...), i like vogella tutorials, so i might give it another try later.

http://www.vogella.com/tutorials/AndroidTesting/article.html

For now, i will read the followings (as i will be adding more references in here):

http://www.toptal.com/android/testing-like-a-true-green-droid

"Because it exercises your full HTTP stack, you can be confident that you're testing everything. You can even copy & paste HTTP responses from your real web server to create representative test cases. Or test that your code survives in awkward-to-reproduce situations like 500 errors or slow-loading responses."
Seems pretty useful... will match perfectly (i think) with what i need for now, I will read it later.

https://github.com/square/okhttp/tree/master/mockwebserver
http://developer.android.com/tools/testing/testing_android.html

I needed to calculate the period between 2 dates, because i didn't want to refetch the json everytime, so i've decided to use JodaTime for this.
I think i have the stupidity prize of the year. To compare a "period of time, measured in days", i need to do the following:

period.normalizedStandard(PeriodType.days()).getDays()

If i only use period.getDays() it will return something like (comparing today vs +10 days) 2! Yay F* logics.

Yeap, i won't be able to finish it "on time" so let's make this thing right. I will make the update query and update policy. 